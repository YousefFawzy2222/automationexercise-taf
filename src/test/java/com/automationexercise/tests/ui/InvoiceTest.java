package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementApi;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.*;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI Payment Management")
@Story("Payment")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
@UITest
public class InvoiceTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    @Test
    @Step("Register with a new account")
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
    @Step("Login wth the created Account")

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
    @Step("Add a Product To Cart")
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
    @Step("Checkout with the added product")
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

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount", "addProductToCart", "checkout"})
    public void paymentTC(){
        new CheckoutPage(driver)
                .clickOnPlaceOrder()
                .fillCardInfo(
                        testData.getJsonData("cardInfo.cardName"),
                        testData.getJsonData("cardInfo.cardNumber"),
                        testData.getJsonData("cardInfo.cardCVC"),
                        testData.getJsonData("cardInfo.cardMonth"),
                        testData.getJsonData("cardInfo.cardYear")
                )
                .verifyPagePaymentSuccessMsg(testData.getJsonData("messages.SuccessPayment"));
    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount", "addProductToCart", "checkout", "paymentTC"})
    public void downloadInvoiceTC(){
        new PaymentPage(driver)
                .clickOnInvoiceButton()
                .verifyDownloadFile(testData.getJsonData("invoiceName"));
    }

    @Test(dependsOnMethods = {"paymentTC","loginToAccount", "registerNewAccount", "addProductToCart", "checkout"})
    @Step("Delete the Account")
    public void deleteAccountAsPostCondition(){
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @BeforeClass
    private void setUp(){
        testData = new JsonReader("payment-data");
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")) {
            driver.browser().closeExtensionTab();
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
