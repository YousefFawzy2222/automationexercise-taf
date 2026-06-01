package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private GUIDriver driver;
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }


    //Locators
    private final By name = By.cssSelector("[data-qa='name']");
    private final By email = By.cssSelector("[data-qa='email']");
    private final By password = By.id("password");
    private final By dayOfBirth = By.id("days");
    private final By monthOfBirth = By.id("months");
    private final By yearOfBirth = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private final By accountCreatedLabel = By.tagName("p");
    private final By countinueButton = By.cssSelector("[data-qa='continue-button']");

    //Actions


     // @param title - Mr or Mrs
    @Step("Choose title {title} in signup form")
    private SignupPage choseTitle (String title){
        By titleLocator = By.xpath("//input[@value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }
    @Step("Fill Registration Form")
    public SignupPage fillRegistrationForm(String title,
                                           String passwordText,
                                           String dayText,
                                           String monthText,
                                           String yearText,
                                           String firstNameText,
                                           String lastNameText,
                                           String companyText,
                                           String address1Text,
                                           String address2Text,
                                           String countryText,
                                           String stateText,
                                           String cityText,
                                           String zipcodeText,
                                           String mobileNumberText){
        choseTitle(title);
        driver.element().selectFromDropDown(dayOfBirth, dayText);
        driver.element().selectFromDropDown(monthOfBirth, monthText);
        driver.element().selectFromDropDown(yearOfBirth, yearText);
        driver.element().click(newsletterCheckbox);
        driver.element().click(offersCheckbox);
        driver.element().type(password, passwordText);
        driver.element().type(firstName, firstNameText);
        driver.element().type(lastName, lastNameText);
        driver.element().type(company, companyText);
        driver.element().type(address1, address1Text);
        driver.element().type(address2, address2Text);
        driver.element().selectFromDropDown(country, countryText);
        driver.element().type(state, stateText);
        driver.element().type(city, cityText);
        driver.element().type(zipcode, zipcodeText);
        driver.element().type(mobileNumber, mobileNumberText);
        return this;
    }

    @Step("Click on Create Account Button")
    public SignupPage clickCreateAccountButton(){
        driver.element().click(createAccountButton);
        return this;
    }

    @Step("Click Continue Button")
    public NavigationBarComponent navigate(){
        driver.element().click(countinueButton);
        return new NavigationBarComponent(driver);
    }

    //Validation
    @Step("Verify Account Created Successfully")
    public SignupPage verifyAccountCreated() {
        driver.verification().isElementVisible(accountCreatedLabel);
        return this;
    }

}
