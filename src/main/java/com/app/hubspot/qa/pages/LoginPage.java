package com.app.hubspot.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.app.hubspot.qa.base.TestBase;
import com.app.hubspot.qa.util.TestUtil;

public class LoginPage extends TestBase {
	
	@FindBy(xpath="//input[@id='username']") 
	WebElement userNameTextBox;
	@FindBy(xpath="//input[@id='password']") 
	WebElement passwordTextBox;
	@FindBy(xpath="//button[@id='loginBtn']") 
	WebElement loginButton;
	
	public String verifyLoginPageTitle() {
		return driver.getTitle();
	}
	
	public void doLogin(String user, String password) {
		TestUtil.sendKeys(driver, userNameTextBox, 5, user);
		TestUtil.sendKeys(driver, passwordTextBox, 5, password);
		TestUtil.clickOnElement(driver, loginButton, 5);
	}
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

}
