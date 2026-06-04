package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.ProductDetailsPage;
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

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("Products Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
public class ProductsDetailsTest extends BaseTest {

    //Tests
    @Test
    public void verifyProductDetailsWithoutLoginTC()
    {
        new ProductsPage(driver)
                .navigate()
                .clickOnViewProduct(testData.getJsonData("product.productName"))
                .verifyProductDetails(
                        testData.getJsonData("product.productName"),
                        testData.getJsonData("product.productPrice")
                );
    }

    @Test
    public void verifyReviewMsgWithoutLoginTC(){
    new ProductsPage(driver)
            .navigate()
            .clickOnViewProduct(testData.getJsonData("product.productName"))
            .addReview(
                    testData.getJsonData("review.name"),
                    testData.getJsonData("review.email"),
                    testData.getJsonData("review.review")
            )
            .verifyReviewMsg(testData.getJsonData("messages.reviewMsg"));

    }


    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("product-details-data");
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
