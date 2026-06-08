package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementApi;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Checkout Management")
@Feature("UI Checkout Management")
@Story("Cart Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
public class CheckoutTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    //When we have a scenario like the checkout scenario u have to include dependencies to know if something went wrong where did it go wrong exactly
    @Test
    //Step 1: Register with a new account
    public void registerNewAccount(){
        new UserManagementApi().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp +"@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("titleMale"),
                testData.getJsonData("day"),
                testData.getJsonData("month"),
                testData.getJsonData("year"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"),
                testData.getJsonData("company"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("country"),
                testData.getJsonData("state"),
                testData.getJsonData("city"),
                testData.getJsonData("zipCode"),
                testData.getJsonData("mobileNumber")
        ). verifyUserCreatedSuccessfully();
    }

    @Test(dependsOnMethods = "registerNewAccount")
    //Step 2: Login to Account
    public void loginToAccount(){
        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));
    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    //Step 3:Add a Product To Cart
    public void addProductToCart(){
        new ProductsPage(driver)
                .navigate()
                .clickOnAddProduct(testData.getJsonData("product.productName"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(
                        testData.getJsonData("product.productName"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total")
                );
    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount", "addProductToCart"})
    //Step 4: Checkout
    public void checkout(){
        new CartPage(driver)
                .clickOnProceedToCheckOut()
                .verifyDeliveryAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")

                        ).verifyBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")

                );
    }





    @BeforeClass
    private void setUp(){
        testData = new JsonReader("checkout-data");
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")) {
            driver.browser().closeExtensionTab();
        }
    }

    @AfterClass
    public void tearDown() {
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
        driver.quitDriver();
    }
}
