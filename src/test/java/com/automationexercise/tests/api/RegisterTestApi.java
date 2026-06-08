package com.automationexercise.tests.api;

import com.automationexercise.apis.UserManagementApi;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTestApi extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    @Test
    public void registerTest(){
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

    @BeforeClass
    protected void setUp(){
        testData = new JsonReader("register-data");
    }
}
