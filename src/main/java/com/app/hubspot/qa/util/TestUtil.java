package com.app.hubspot.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.app.hubspot.qa.base.TestBase;

public class TestUtil extends TestBase {
	
	public static final long IMPLICIT_WAIT=10;
	public static final long PAGE_LOAD_WAIT=20;
	
	public static void sendKeys(WebDriver driver, WebElement ele, int time, String value) {
		new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(ele));
		ele.sendKeys(value);
	}
	
	public static void clickOnElement(WebDriver driver, WebElement ele, int time) {
		new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(ele));
		ele.click();
	}
	
	public static String getScreenshot(WebDriver driver, String imageName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date d = new Date();
		String timestamp = s.format(d);
		String destination = System.getProperty("user.dir")+"/screenshots/"+imageName+"_"+timestamp+".png";
		File destFile = new File(destination);
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}
}
