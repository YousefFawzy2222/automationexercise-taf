package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;
    public NavigationBarComponent navigationBar;

    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }
    //variable
    private String productPage = "products";

    //locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedLabel = By.cssSelector(".modal-body > p");
    private final By viewCartButton = By.cssSelector("p > [href='/view_cart']");
    private final By continueShoppingButton = By.cssSelector(".modal-footer > button");

    //dynamic locator
    private By productName(String productName){
        return By.xpath("//div[@class='productinfo text-center']/p[normalize-space()='" + productName + "']");
    }
    private By productPrice(String productName){
        return By.xpath("//div[@class='productinfo text-center'][p[normalize-space()='" + productName + "']]/h2");
    }

    private By hoverOnProduct(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='"+productName+"']");
    }
    private By addToCartButton(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='"+productName+"'] //following-sibling::a");
    }
    private By viewProduct(String productName){
        return By.xpath("//p[.='"+productName+"'] //following::div[@class='choose'][1]");
    }

    //actions
    @Step("Navigate to products page")
    public ProductsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+productPage);
        return this;
    }
    @Step("Search for product: {productName}")
    public ProductsPage searchProduct(String productName){
        driver.element().type(searchField,productName).click(searchButton);
        return this;
    }

    @Step("Click on add to cart for product: {productName}")
    public ProductsPage clickOnAddProduct(String productName){
        driver.element().hover(hoverOnProduct(productName))
                .click(addToCartButton(productName));
        return this;
    }

    @Step("Click on view product for product: {productName}")
    public ProductDetailsPage clickOnViewProduct(String productName) {
        driver.element().click(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }
    @Step("Click on View Cart")
    public ProductsPage clickOnViewCart(){
        driver.element().click(viewCartButton);
        return this;
    }
    //validations
    @Step("Validate product details")
    public ProductsPage validateProductDetails(String productName, String productPrice){
        String actualProductName = driver.element().hover(productName(productName)).getText(productName(productName));
        String actualProductPrice = driver.element().hover(productName(productName)).getText(productPrice(productName));
        LogsManager.info("Actual Product Name: " + actualProductName);
        LogsManager.info("Actual Product Price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added label contains: {expectedText}")
    public ProductsPage validateItemAddedLabel(String expectedText){
        String actualText = driver.element().getText(itemAddedLabel);
        LogsManager.info("Actual Item Added Label: " + actualText);
        driver.verification().Equals(actualText, expectedText, "Item added label does not match expected text");
        return this;
    }
}
