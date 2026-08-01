package com.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.io.*;
import java.util.*;

public class LinkedInAutoApply {

    WebDriver driver;
    WebDriverWait wait;
    Set<String> appliedJobs = new HashSet<>();
    PrintWriter csvLogger;

    // ── Your profile data ──────────────────────────
    final String EMAIL    = "ganeshmahure199@gmail.com";
    final String PASSWORD = "YOUR_PASSWORD";
    final String PHONE    = "9823263616";
    final String CITY     = "Mumbai";
    final String YEARS_EXP = "4";
    final String NOTICE   = "Immediate";
    // ───────────────────────────────────────────────

    public void init() throws Exception {
        driver = new ChromeDriver();
        wait   = new WebDriverWait(driver, Duration.ofSeconds(12));
        csvLogger = new PrintWriter(new FileWriter("applied_jobs.csv", true));
        csvLogger.println("Job Title,Company,Date Applied,URL");
    }

    // STEP 1 — Login
    public void login() throws InterruptedException {
        driver.get("https://www.linkedin.com/login");
        driver.findElement(By.id("username")).sendKeys(EMAIL);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(3000);
    }

    // STEP 2 — Search QA jobs with Easy Apply filter
    public void searchJobs() {
        String url = "https://www.linkedin.com/jobs/search/?" +
            "keywords=QA+Automation+Engineer+Selenium+Java" +
            "&location=India" +
            "&f_LF=f_AL" +       // Easy Apply filter
            "&f_TPR=r604800" +   // Posted last 7 days
            "&f_E=2%2C3" +       // Mid-Senior level
            "&sortBy=DD";        // Sort by date
        driver.get(url);
    }

    // STEP 3 — Iterate through job cards
    public void applyToJobs() throws Exception {
        int page = 0;
        while (page < 5) { // Loop through 5 pages
            Thread.sleep(3000);
            List<WebElement> jobCards = driver.findElements(
                By.cssSelector(".job-card-container--clickable")
            );

            for (WebElement card : jobCards) {
                try {
                    scrollTo(card);
                    card.click();
                    Thread.sleep(2000);
                    applyToCurrentJob();
                } catch (Exception e) {
                    System.out.println("Skipped job: " + e.getMessage());
                    dismissModal();
                }
            }

            // Go to next page
            nextPage(++page);
        }
        csvLogger.close();
    }

    // STEP 4 — Apply to the open job
    private void applyToCurrentJob() throws Exception {
        // Get job details for logging
        String title   = getText(".job-details-jobs-unified-top-card__job-title");
        String company = getText(".job-details-jobs-unified-top-card__company-name");
        String jobUrl  = driver.getCurrentUrl();

        if (appliedJobs.contains(jobUrl)) return; // Skip duplicates

        // Click Easy Apply button
        WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".jobs-apply-button--top-card button")
        ));
        if (!applyBtn.getText().contains("Easy Apply")) return;
        applyBtn.click();
        Thread.sleep(1500);

        // Handle multi-step modal
        handleApplicationModal(title, company, jobUrl);
    }

    // STEP 5 — Fill multi-step application modal
    private void handleApplicationModal(String title, String company, String url)
            throws Exception {
        int maxSteps = 8;
        for (int step = 0; step < maxSteps; step++) {
            Thread.sleep(1500);

            // Fill phone number
            fillIfEmpty(By.cssSelector("input[id*='phoneNumber']"), PHONE);

            // Fill city
            fillIfEmpty(By.cssSelector("input[id*='city']"), CITY);

            // Answer YES/NO radio questions
            answerYesNoQuestions();

            // Answer numeric fields (years of experience, etc.)
            answerNumericFields();

            // Answer dropdown selects
            answerDropdowns();

            // Check if Submit button is visible
            if (isPresent(By.cssSelector("button[aria-label='Submit application']"))) {
                driver.findElement(
                    By.cssSelector("button[aria-label='Submit application']")
                ).click();

                // Log success
                System.out.println("✅ Applied: " + title + " @ " + company);
                csvLogger.println(title + "," + company + "," + new Date() + "," + url);
                appliedJobs.add(url);
                Thread.sleep(2000);

                // Close confirmation
                dismissModal();
                return;
            }

            // Click Next/Continue/Review
            clickNext();
        }
    }

    // ── Helper: answer Yes/No questions ────────────
    private void answerYesNoQuestions() {
        List<WebElement> radios = driver.findElements(
            By.cssSelector("input[type='radio'][value='Yes']")
        );
        for (WebElement r : radios) {
            try { if (!r.isSelected()) r.click(); }
            catch (Exception ignored) {}
        }
    }

    // ── Helper: fill numeric experience fields ──────
    private void answerNumericFields() {
        List<WebElement> inputs = driver.findElements(
            By.cssSelector("input[type='text'], input[type='number']")
        );
        for (WebElement input : inputs) {
            try {
                String label = input.getAttribute("id").toLowerCase();
                if (label.contains("year") || label.contains("experience")) {
                    if (input.getAttribute("value").isEmpty())
                        input.sendKeys(YEARS_EXP);
                }
            } catch (Exception ignored) {}
        }
    }

    // ── Helper: answer dropdowns ────────────────────
    private void answerDropdowns() {
        List<WebElement> selects = driver.findElements(By.tagName("select"));
        for (WebElement sel : selects) {
            try {
                Select s = new Select(sel);
                String label = sel.getAttribute("id").toLowerCase();
                if (label.contains("notice") || label.contains("availab"))
                    s.selectByIndex(1); // first option = immediate
                else if (label.contains("experience"))
                    s.selectByVisibleText("4 years");
                else if (s.getFirstSelectedOption().getText().contains("Select"))
                    s.selectByIndex(1);
            } catch (Exception ignored) {}
        }
    }

    // ── Helper: click Next / Continue / Review ──────
    private void clickNext() {
        String[] labels = {
            "Continue to next step",
            "Review your application",
            "Next",
            "Continue"
        };
        for (String label : labels) {
            try {
                driver.findElement(
                    By.cssSelector("button[aria-label='" + label + "']")
                ).click();
                return;
            } catch (Exception ignored) {}
        }
    }

    private void fillIfEmpty(By by, String value) {
        try {
            WebElement el = driver.findElement(by);
            if (el.getAttribute("value").isEmpty()) {
                el.clear(); el.sendKeys(value);
            }
        } catch (Exception ignored) {}
    }

    private String getText(String css) {
        try { return driver.findElement(By.cssSelector(css)).getText(); }
        catch (Exception e) { return "Unknown"; }
    }

    private boolean isPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    private void scrollTo(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView(true);", el
        );
    }

    private void dismissModal() {
        try {
            driver.findElement(
                By.cssSelector("button[aria-label='Dismiss']")
            ).click();
            Thread.sleep(800);
            driver.findElement(
                By.cssSelector("button[data-control-name='discard_application_confirm_btn']")
            ).click();
        } catch (Exception ignored) {}
    }

    private void nextPage(int page) throws InterruptedException {
        try {
            driver.findElement(
                By.cssSelector("button[aria-label='Page " + page + "']")
            ).click();
            Thread.sleep(3000);
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) throws Exception {
        LinkedInAutoApply bot = new LinkedInAutoApply();
        bot.init();
        bot.login();
        bot.searchJobs();
        bot.applyToJobs();
    }
}