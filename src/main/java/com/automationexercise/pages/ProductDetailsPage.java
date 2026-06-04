package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;
    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Variables
    private String productDetailsEndpoint = "product_details/2";

    //Locators
    private final By productName = By.cssSelector(".product-information > h2");
    private final By productPrice = By.cssSelector(".product-information > span > span");

    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By reviweButton = By.id("button-review");
    private final By reviewMsg = By.cssSelector("#review-section span"); //Thank you for your review.

    //Actions
    @Step("Navigate to product details page")
    public ProductDetailsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + productDetailsEndpoint);
        return this;
    }

    @Step("Add review with name: {name}, email: {email} and review: {review}")
    public ProductDetailsPage addReview(String name, String email, String review){
        driver.element()
                .type(this.name,name)
                .type(this.email,email)
                .type(this.reviewTextArea,review)
                .click(this.reviweButton);
        return this;
    }

    //Validation
    @Step("Verify product details: name = {name}, price = {price}")
    public ProductDetailsPage verifyProductDetails(String name, String price){
        String actualProductName = driver.element().getText(productName);
        String actualProductPrice = driver.element().getText(productPrice);
        LogsManager.info("Actual Product Name: " + actualProductName);
        LogsManager.info("Actual Product Price: " + actualProductPrice);
        driver.validation().Equals(actualProductName,name,"Product name does not match");
        driver.validation().Equals(actualProductPrice,price,"Product price does not match");
        return this;
    }

    @Step("Verify review message: {msg}")
    public ProductDetailsPage verifyReviewMsg(String msg){
        String actualReviewMsg = driver.element().getText(reviewMsg);
        LogsManager.info("Actual Review Message: " + actualReviewMsg);
        driver.validation().Equals(actualReviewMsg,msg,"Review message does not match");
        return this;
    }
}
