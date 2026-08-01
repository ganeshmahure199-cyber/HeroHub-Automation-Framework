package com.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import OrangeHRM.Utility.BaseTest;

public class FindBrokenLink extends BaseTest {

	@Test
	public void FindBrokenLinks() {
		
		
		    String targetUrl = "https://www.flipkart.com/"; 
	        driver.get(targetUrl);

	        List<WebElement> links = driver.findElements(By.tagName("a"));
	        System.out.println("Total links found: " + links.size());
	        for (WebElement link : links) {
	            String url = link.getAttribute("href");
	             if (url == null || url.isEmpty() || url.startsWith("javascript")) {
	                System.out.println("URL is either empty or a javascript void. Skipping.");
	                continue;
	            }
	            checkLinkStatus(url);
	        }
	        driver.quit();
	    }
	    
	    public static void checkLinkStatus(String linkUrl) {
	        try {
	            URL url = new URL(linkUrl);
	            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
	            httpURLConnect.setConnectTimeout(3000);
	            httpURLConnect.connect();
	            if (httpURLConnect.getResponseCode() >= 400) {
	                System.out.println("BROKEN LINK: " + linkUrl + " ---> " + httpURLConnect.getResponseMessage() + " (" + httpURLConnect.getResponseCode() + ")");
	            } else {
	                System.out.println("VALID LINK: " + linkUrl + " ---> " + httpURLConnect.getResponseMessage());
	            }	            
	        } catch (IOException e) {
	            System.out.println("ERROR checking URL: " + linkUrl + " ---> " + e.getMessage());
	        }
	    }
}
