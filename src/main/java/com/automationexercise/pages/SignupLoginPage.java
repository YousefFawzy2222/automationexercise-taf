package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    public NavigationBarComponent navigationBar;
    GUIDriver driver;
    private final String signUpLoginEndpoint = "/signup";
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //Locators

    //Login From
    private final By loginEmail = By.cssSelector("[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("[data-qa=\"login-password\"]");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
    //SignUp Form
    private final By signUpName = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By signUpEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signUpButton = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By signupLabel = By.cssSelector(".signup-form > h2");
    private final By loginError = By.cssSelector(".login-form p");
    private final By signUpError = By.cssSelector(".signup-form p");


    //Actions
    @Step("Navigate to Signup/Login Page")
    public SignupLoginPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ signUpLoginEndpoint);
        return this;
    }

    @Step("Enter email {email} in login field")
    public SignupLoginPage enterLoginEmail(String email){
        driver.element().type(loginEmail,email);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(loginPassword,password);
        return this;
    }

    @Step("Click on Login Button")
    public SignupLoginPage clickLoginButton(){
        driver.element().click(loginButton);
        return this;
    }

    @Step("Enter name {name} in signup field")
    public SignupLoginPage enterSignUpName(String name){
        driver.element().type(signUpName,name);
        return this;
    }

    @Step("Enter email {email} in signup field")
    public SignupLoginPage enterSignUpEmail(String email){
        driver.element().type(signUpEmail,email);
        return this;
    }

    @Step("Click on Signup Button")
    public SignupLoginPage clickSignUpButton(){
        driver.element().click(signUpButton);
        return new SignupLoginPage(driver);
    }

    //Validation
    @Step("Verify new user signup visible")
    public SignupLoginPage verifyNewUserSignupVisible(){
        driver.verification().isElementNotVisible(signupLabel);
        return this;
    }

    @Step("Verify login error message {errorExpected}")
    public SignupLoginPage verifyLoginErrorMsg(String errorExpected){
        String errorActual = driver.element().getText(loginError);
        driver.verification().Equals(errorActual,errorExpected, "Login error message is not as expected");
        return this;
    }

    @Step("Verify signup error message {errorExpected}")
    public SignupLoginPage verifySignUpErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(signUpError);
        driver.verification().Equals(errorActual, errorExpected, "Signup error message is not as expected");
        return this;
    }
}
