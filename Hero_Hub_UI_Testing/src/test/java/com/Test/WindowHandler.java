package com.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Set;

public class WindowHandler {
	
	    public static void main(String[] args) {
	        WebDriver driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get("https://example.com"); // Replace with your target URL

	        // 1. Save the parent window handle immediately
	        String parentWindowHandle = driver.getWindowHandle();
	        System.out.println("Parent Window Handle: " + parentWindowHandle);

	        // Simulation: Logic to open multiple windows (e.g., clicking 9 links)
	        for (int i = 1; i <= 9; i++) {
	            // driver.findElement(By.id("open-window-btn")).click(); 
	        }

	        // 2. Switch to the 10th window (for simulation purposes)
	        Set<String> allWindowHandles = driver.getWindowHandles();
	        for (String handle : allWindowHandles) {
	            driver.switchTo().window(handle);
	        }
	        System.out.println("Currently on Window Title: " + driver.getTitle());

	        // 3. Jump directly back to the parent window without using an index
	        driver.switchTo().window(parentWindowHandle);
	        System.out.println("Successfully returned to Parent Window Title: " + driver.getTitle());

	        // 4. Optional: Close all other windows except the parent
	        for (String handle : driver.getWindowHandles()) {
	            if (!handle.equals(parentWindowHandle)) {
	                driver.switchTo().window(handle);
	                driver.close();
	            }
	        }
	        
	        // Switch back to parent to continue test execution
	        driver.switchTo().window(parentWindowHandle);

	        // Close parent browser session
	        driver.quit();	    
	}	
}
