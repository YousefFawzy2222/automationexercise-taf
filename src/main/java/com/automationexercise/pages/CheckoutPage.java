package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    public CheckoutPage(GUIDriver driver){
        this.driver = driver;
    }

    private String checkoutPageEndpoint = "checkout";

    //locators
    private final By placeOrderButton = By.xpath("//a[.='Place Order']");
    //delivery address locators
    private final By deliveryName = By.xpath("//ul[@id='address_delivery'] /li[@class='address_firstname address_lastname']");
    private final By deliveryCompany = By.xpath("//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'][1]");
    private final By deliveryAddress1 = By.xpath("//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'][2]");
    private final By deliveryAddress2 = By.xpath("//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'][3]");
    private final By deliveryCityStateZipcode = By.xpath("//ul[@id='address_delivery'] /li[@class='address_city address_state_name address_postcode']");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery'] /li[@class='address_country_name']");
    private final By deliveryPhone = By.xpath("//ul[@id='address_delivery'] /li[@class='address_phone']");
    //billing address locators
    private final By billingName = By.xpath("//ul[@id='address_invoice'] /li[@class='address_firstname address_lastname']");
    private final By billingCompany = By.xpath("//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'][1]");
    private final By billingAddress1 = By.xpath("//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'][2]");
    private final By billingAddress2 = By.xpath("//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'][3]");
    private final By billingCityStateZipcode = By.xpath("//ul[@id='address_invoice'] /li[@class='address_city address_state_name address_postcode']");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice'] /li[@class='address_country_name']");
    private final By billingPhone = By.xpath("//ul[@id='address_invoice'] /li[@class='address_phone']");

    //Actions
    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate(){
        driver
                .browser()
                .navigateTo(PropertyReader.getProperty("baseUrlWeb") + checkoutPageEndpoint);
        return this;
    }

    @Step("Click on Place order Button")
    public OrderPage clickOnPlaceOrder(){
        driver.element().click(placeOrderButton);
        return new OrderPage(driver);

    }

    //Validation
    @Step("Verify Delivery Address Details")
    public CheckoutPage verifyDeliveryAddress (String title,
                                               String fName,
                                               String lName,
                                               String company,
                                               String address1,
                                               String address2,
                                               String city,
                                               String state,
                                               String zipcode,
                                               String country,
                                               String phone)
    {
        driver.validation()
                .Equals(driver.element().getText(deliveryName), (title + ". " + fName + " "+ lName), "Delivery Name is not matched")
                .Equals(driver.element().getText(deliveryCompany), company, "Delivery Company Name is not matched")
                .Equals(driver.element().getText(deliveryAddress1), address1, "Delivery address1 is not matched")
                .Equals(driver.element().getText(deliveryAddress2), address2, "Delivery address2 is not matched")
                .Equals(driver.element().getText(deliveryCityStateZipcode), (zipcode + " " +city +" "+state), "Delivery CitStateZipcode is not matched")
                .Equals(driver.element().getText(deliveryCountry), country, "Delivery Country Name is not matched")
                .Equals(driver.element().getText(deliveryPhone), phone, "Delivery Phone Number is not matched");
        return this;
    }

    @Step("Verify Billing Address Details")
    public CheckoutPage verifyBillingAddress (String title,
                                               String fName,
                                               String lName,
                                               String company,
                                               String address1,
                                               String address2,
                                               String city,
                                               String state,
                                               String zipcode,
                                               String country,
                                               String phone)
    {
        driver.validation()
                .Equals(driver.element().getText(billingName), (title + ". " + fName + " "+ lName), "Billing Name is not matched")
                .Equals(driver.element().getText(billingCompany), company, "Billing Company Name is not matched")
                .Equals(driver.element().getText(billingAddress1), address1, "Billing address1 is not matched")
                .Equals(driver.element().getText(billingAddress2), address2, "Billing address2 is not matched")
                .Equals(driver.element().getText(billingCityStateZipcode), (zipcode + " " +city +" "+state), "Billing CitStateZipcode is not matched")
                .Equals(driver.element().getText(billingCountry), country, "Billing Country Name is not matched")
                .Equals(driver.element().getText(billingPhone), phone, "Billing Phone Number is not matched");
        return this;
    }


}
