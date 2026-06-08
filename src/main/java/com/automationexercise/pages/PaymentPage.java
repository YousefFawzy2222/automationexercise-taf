package com.automationexercise.pages;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private GUIDriver driver;
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }

    //var
    private String paymentEndpoint="payment";

    //locators
    private final By nameOnCard = By.name("name_on_card");//card_number
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expireMonth = By.name("expiry_month");
    private final By expireYear = By.name("expiry_year");
    private final By payButton = By.id("submit");

    private final By successMsg = By.cssSelector("h2 > b");
    private final By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");

    //actions
    @Step("Navigate to Payment Page")
    public PaymentPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + paymentEndpoint);
        return this;
    }


    @Step("Fill card Info")
    public PaymentPage fillCardInfo(String nameOnCard,
                                    String cardNumber,
                                    String cvc,
                                    String expireMonth,
                                    String expireYear){
        driver.element()
                .type(this.nameOnCard, nameOnCard)
                .type(this.cardNumber, cardNumber)
                .type(this.cvc, cvc)
                .type(this.expireMonth, expireMonth)
                .type(this.expireYear, expireYear)
                .click(payButton);
        return this;
    }
    @Step("Click on Download Invoice Button")
    public PaymentPage clickOnInvoiceButton(){
        driver.element().click(downloadInvoiceButton);
        return this;
    }


    //validations

    @Step("Validate on Page success message")
    public PaymentPage verifyPagePaymentSuccessMsg(String expectedMsg){
        String successMsg = driver.element().getText(this.successMsg);
        driver.verification().Equals(successMsg, expectedMsg, "Payment Success Message does not match!");
        return this;
    }

    public PaymentPage verifyDownloadFile(String invoiceName) {
        driver.verification().assertFileExists(invoiceName, "File does not exist");
        return this;
    }
}
