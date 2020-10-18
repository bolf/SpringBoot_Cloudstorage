package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id="inputFirstname")
    private WebElement inputFirstname;

    @FindBy(id="inputLastname")
    private WebElement inputLastname;

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="submit-button")
    private WebElement submit_button;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public boolean signup(String fName,String lName, String uName, String password,WebDriver driver) throws NoSuchElementException{
        inputFirstname.sendKeys(fName);
        inputLastname.sendKeys(lName);
        inputPassword.sendKeys(password);
        inputUsername.sendKeys(uName);
        submit_button.click();

        return driver.findElement(By.id("success-msg")).isDisplayed();
    }
}
