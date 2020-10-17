package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id="buttonLogout")
    private WebElement buttonLogout;

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="saveChangesButton")
    private WebElement saveChangesButton;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public boolean logout(WebDriver driver) {
        buttonLogout.click();
        //element with id "buttonLogout" is no more available means we logged out
        try {
            WebElement marker = driver.findElement(By.id("buttonLogout"));
            return marker != null;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean postNote(WebDriver driver){
        notesTab.click();
        addNoteButton.click();
        noteTitle.sendKeys("my test #$% note title");
        noteDescription.sendKeys("my test #$% note description");
        saveChangesButton.click();
        return driver.getPageSource().contains("my test #$% note title") && driver.getPageSource().contains("my test #$% note description");
    }

}
