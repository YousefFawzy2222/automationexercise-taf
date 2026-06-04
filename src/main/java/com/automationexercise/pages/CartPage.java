package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;

    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Variables
    private String cartPageEndpoint = "view_cart";

    //Locators
    private final By proceedToCheckoutButton = By.xpath("//a[.='Proceed To Checkout']");

    //Dynamic Locators
    private By prooductName(String productName) {
        return By.xpath("(//h4 /a[.='" + productName + "'])[1]");
    }

    private By prooductPrice(String productName) {
        return By.xpath("(//h4 /a[.='" + productName + "']//following::td[@class='cart_price']/p)[1]");
    }

    private By prooductQuantity(String productName) {
        return By.xpath("(//h4 /a[.='" + productName + "']//following::td[@class='cart_quantity']/button)[1]");
    }

    private By prooductTotal(String productName) {
        return By.xpath("(//h4 /a[.='" + productName + "']//following::td[@class='cart_total']/p)[1]");
    }

    private By removeProduct(String productName) {
        return By.xpath("(//h4 /a[.='" + productName + "']//following::td[@class='cart_delete']/a)[1]");
    }

    //Actions
    @Step("Navigate to Cart Page")
    public CartPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + cartPageEndpoint);
        return this;
    }

    @Step("Click on Proceed To Checkout button")
    public CartPage clickOnProceedToCheckOut(){
        driver.element().click(proceedToCheckoutButton);
        return this;
    }

    @Step("Remove product: {productName}")
    public CartPage ClickOnRemoveProductButton(String productName){
        driver.element().click(removeProduct(productName));
        return this;
    }

    //Validations
    @Step("Verify product details on Cart Page for product: {productName}")
    public CartPage verifyProductDetailsOnCart(String productName, String price, String quantity, String total) {
        String actualProductName = driver.element().getText(prooductName(productName));
        String actualPrice = driver.element().getText(prooductPrice(productName));
        String actualQuantity = driver.element().getText(prooductQuantity(productName));
        String actualTotal = driver.element().getText(prooductTotal(productName));
        driver.validation().Equals(actualProductName, productName, "Product name does not match")
                .Equals(actualPrice, price, "Product price does not match")
                .Equals(actualQuantity, quantity, "Product quantity does not match")
                .Equals(actualTotal, total, "Product total does not match");
        return this;
    }

}
