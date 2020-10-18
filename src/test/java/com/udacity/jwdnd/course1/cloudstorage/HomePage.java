package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public boolean logout(WebDriver driver) {
        driver.findElement(By.id("buttonLogout")).click();;

        //element with id "buttonLogout" is no more available means we logged out
        try {
            WebElement buttonLogout = driver.findElement(By.id("buttonLogout"));
            return buttonLogout != null;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean postNote(WebDriver driver){
        String noteTitleText = "my test note";
        String noteDescriptionText = "my test note description";

        driver.findElement(By.id("nav-notes-tab")).click();
        driver.findElement(By.id("addNoteButton")).click();
        driver.findElement(By.id("note-title")).sendKeys(noteTitleText);
        driver.findElement(By.id("note-description")).sendKeys(noteDescriptionText);
        driver.findElement(By.id("saveChangesButton")).click();

        String displayedNoteTitleText = driver.findElement(By.cssSelector("th.noteTitle")).getText();
        String displayedNoteDescriptionText = driver.findElement(By.cssSelector("td.noteDescription")).getText();

        return displayedNoteTitleText.equals(noteTitleText) && displayedNoteDescriptionText.equals(noteDescriptionText);
    }

    public boolean editNote(WebDriver driver){
        String noteTitleText = "my test note edited";
        String noteDescriptionText = "my test note description edited";

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));

        driver.findElement(By.className("editNoteBtn")).click();
        noteTitle.clear();
        noteTitle.sendKeys(noteTitleText);
        noteDescription.clear();
        noteDescription.sendKeys(noteDescriptionText);
        driver.findElement(By.id("saveChangesButton")).click();

        String displayedNoteTitleText = driver.findElement(By.cssSelector("th.noteTitle")).getText();
        String displayedNoteDescriptionText = driver.findElement(By.cssSelector("td.noteDescription")).getText();

        return displayedNoteTitleText.equals(noteTitleText) && displayedNoteDescriptionText.equals(noteDescriptionText);
    }

    public boolean deleteNote(WebDriver driver) {
        String noteTitleText = "my test note edited";
        String noteDescriptionText = "my test note description edited";
        driver.findElement(By.cssSelector("td a")).click();
        return !(driver.getPageSource().contains(noteTitleText) || driver.getPageSource().contains(noteDescriptionText));
    }

    public boolean createCredential(WebDriver driver) {
        String password = "googleUserPass";
        String user = "googleUser";
        String url = "https://google.com";

        driver.findElement(By.id("nav-credentials-tab")).click();
        driver.findElement(By.id("addCredentialButton")).click();
        driver.findElement(By.id("credential-url")).sendKeys(url);
        driver.findElement(By.id("credential-username")).sendKeys(user);
        driver.findElement(By.id("credential-password")).sendKeys(password);
        driver.findElement(By.id("saveCredentialChanges")).click();

        String displayedURL = driver.findElement(By.cssSelector("th.credentialURL")).getText();
        String displayedUserName = driver.findElement(By.cssSelector("td.credentialUserName")).getText();
        String displayedPass = driver.findElement(By.cssSelector("td.credentialPassword")).getText();

        return displayedURL.equals(url)
                && displayedUserName.equals(user)
                && !displayedPass.equals(password);
    }

    public boolean editCredential(WebDriver driver) {
        String previousPwd = "googleUserPass";

        String password = "editedGoogleUserPass";
        String user = "editedGoogleUser";
        String url = "https://google.com/mail";

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));

        driver.findElement(By.className("editCredentialBtn")).click();
        boolean prevPwdIsDecrypted = credentialPassword.getAttribute("value").equals(previousPwd);
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
        credentialUsername.clear();
        credentialUsername.sendKeys(user);
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
        driver.findElement(By.id("saveCredentialChanges")).click();

        String displayedURL = driver.findElement(By.cssSelector("th.credentialURL")).getText();
        String displayedUserName = driver.findElement(By.cssSelector("td.credentialUserName")).getText();
        String displayedPass = driver.findElement(By.cssSelector("td.credentialPassword")).getText();

        return prevPwdIsDecrypted
                && displayedURL.equals(url)
                && displayedUserName.equals(user)
                && !displayedPass.equals(password);
    }

    public boolean deleteCredential(WebDriver driver) {
        String user = "editedGoogleUser";
        String url = "https://google.com/mail";
        driver.findElement(By.className("deleteCredentialLink")).click();
        return !(driver.getPageSource().contains(user) || driver.getPageSource().contains(url));
    }
}