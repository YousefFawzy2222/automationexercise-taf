package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart Management")
@Feature("UI Cart Details")
@Story("Cart Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
public class CartTest extends BaseTest {

    @Test
    public void verifyProductDetailsOnCartWithoutLoginTC() {
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


    //Configuration
    @BeforeClass
    private void preCondition(){
        testData = new JsonReader("cart-data");
    }
    @BeforeMethod
    public void beforeMethod() {
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")) {
            driver.browser().closeExtensionTab();
        }
    }
    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
