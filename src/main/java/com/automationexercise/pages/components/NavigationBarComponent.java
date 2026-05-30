package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    public final GUIDriver driver;
    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //Locators
    private final By homeButton = By.xpath("//a[text()=' Home']");
    private final By productButton = By.xpath("//a[@href='/products']");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By signupLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By videoTutorial = By.xpath("//a[.=' Video Tutorials']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");

    //Actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Product Button")
    public ProductsPage clickProductButton() {
        driver.element().click(productButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart Button")
    public CartPage clickCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Logout Button")
    public LogoutPage clickLogoutButton() {
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }

    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickSignupLoginButton() {
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Test Cases Button")
    public TestCasesPage clickTestCasesButton() {
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }

    @Step("Click on API Testing Button")
    public ApiTestingPage clickApiButton() {
        driver.element().click(apiButton);
        return new ApiTestingPage(driver);
    }

    @Step("Click on Contact Us Button")
    public ContactUsPage clickContactUsButton() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    @Step("Click on Video Tutorials Button")
    public VideoTutorialPage clickVideoTutorial() {
        driver.element().click(videoTutorial);
        return new VideoTutorialPage(driver);
    }

    //Validation
    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePageLabel() {
        driver.verification().isElementNotVisible(homePageLabel);
        return this;
    }
    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName) {
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Actual User Name: " + actualName);
        driver.verification().assertEquals(actualName, expectedName, "User name does not match expected value");
        return this;
        }
}
