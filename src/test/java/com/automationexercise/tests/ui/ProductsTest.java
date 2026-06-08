package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("Products Management")
@Severity(SeverityLevel.MINOR)
@Owner("Fawzy")
@UITest
public class ProductsTest extends BaseTest {

    @Test
    @Description("Search for a product and validate its details")
    public void searchProductTC() {
        new ProductsPage(driver)
                .navigate()
                .searchProduct(testData.getJsonData("searchProduct.productName"))
                .validateProductDetails(
                        testData.getJsonData("searchProduct.productName"),
                        testData.getJsonData("searchProduct.productPrice")
                );
    }

    @Test
    @Description("Add a product to the cart without logging in")
    public void addProductToCartWithoutLogin(){
        new ProductsPage(driver)
                .navigate()
                .clickOnAddProduct(testData.getJsonData("product.productName"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded")
                );
    }


    //Configurations
    @BeforeClass
    private void preCondition(){
        testData = new JsonReader("products-data");
    }
    //Configurations
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
