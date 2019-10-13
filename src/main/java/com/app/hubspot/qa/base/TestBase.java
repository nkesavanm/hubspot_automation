package com.app.hubspot.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.app.hubspot.qa.util.TestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestBase {
	
	public static Properties pro;
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports reports;
	public static ExtentTest test;
	
	public TestBase() {
		pro = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/app/hubspot/qa/config/config.properties");
			pro.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialization() {
		String browserName = pro.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chrome/chromedriver.exe");
			driver = new ChromeDriver();  
		} 
		else if(browserName.equalsIgnoreCase("firefox")) {
			
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(pro.getProperty("application_url"));
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}
	
	@BeforeSuite
	public void browserSetUp() {
		initialization();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/src/main/java/com/app/hubspot/qa/report/Automation_Execution_Status.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("HubSpot Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);
		reports.setSystemInfo("username", "kesav");
		reports.setSystemInfo("OS", "Windows 8");
		reports.setSystemInfo("Selenium Version", "3.141.59");
		reports.setSystemInfo("Java Version", "Java8");
		reports.setSystemInfo("Host", "localhost");
	}
	
	@AfterMethod
	public void teardown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			String screenshotpath = TestUtil.getScreenshot(driver, result.getName());
			try {
				test.fail("TEST CASE IS FAILED " + result.getName());
				test.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			test.skip("TEST CASE IS SKIPPED "+ result.getName());
		}
	}
	
	@AfterSuite
	public void closeBrowser() {
		reports.flush();
		//driver.quit();
	}
}
