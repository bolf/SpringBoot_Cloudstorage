package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="submit-button")
    private WebElement submit_button;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public boolean login(String username, String password, WebDriver driver) {
        inputPassword.sendKeys(password);
        inputUsername.sendKeys(username);
        submit_button.click();

        return driver.getTitle().equals("Home");
    }
}
