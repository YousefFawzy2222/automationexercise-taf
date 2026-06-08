package com.automationexercise.apis;

import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementApi {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;


    public UserManagementApi() {
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";

    //API Methods
    // name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number
    @Step("Create a new user account with name: {name}, email: {email}, password: {pass}, title: {title}, birth date: {birth_date}, birth month: {birth_month}, birth year: {birth_year}, first name: {firstName}, last name: {lastName}, company: {company}, address1: {address1}, address2: {address2}, country: {country}, zipcode: {zipcode}, state: {state}, city: {city}, mobile number: {mobile_number}")
    public UserManagementApi createRegisterUserAccount(String name,
                                                       String email,
                                                       String pass,
                                                       String title,
                                                       String birth_date,
                                                       String birth_month,
                                                       String birth_year,
                                                       String firstName,
                                                       String lastName,
                                                       String company,
                                                       String address1,
                                                       String address2,
                                                       String country,
                                                       String zipcode,
                                                       String state,
                                                       String city,
                                                       String mobile_number){
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagemenetRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Create a new user account with minimal details")
    public UserManagementApi createRegisterUserAccount(String name,
                                                       String email,
                                                       String pass,
                                                       String firstName,
                                                       String lastName
                                                       ){
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "January");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("zipcode", "123456");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "12345678901");
        response = requestSpecification.spec(Builder.getUserManagemenetRequestSpecification(formParams))
                .post(createAccount_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Delete user account with email: {email}")
    public UserManagementApi deleteUserAccount(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);

        response = requestSpecification
                .spec(Builder.getUserManagemenetRequestSpecification(formParams))
                .delete(deleteAccount_endpoint);

        logResponse();
        return this;
    }

    @Step("Verify that user is created successfully")
    public UserManagementApi verifyUserCreatedSuccessfully() {
        String actualMessage = getResponseMessage();

        verification.Equals(
                actualMessage,
                "User created!",
                "User creation failed. Expected message: 'User created!', but got: " + actualMessage
        );

        return this;
    }

    @Step("Verify that user is deleted successfully")
    public UserManagementApi verifyUserDeletedSuccessfully() {
        String actualMessage = getResponseMessage();

        verification.Equals(
                actualMessage,
                "Account deleted!",
                "User deletion failed. Expected message: 'Account deleted!', but got: " + actualMessage
        );

        return this;
    }

    private String getResponseMessage() {
        String body = response.asString();

        if (body == null || body.isBlank()) {
            throw new AssertionError(
                    "API response body is empty. Status code: "
                            + response.statusCode()
                            + ", content type: "
                            + response.contentType()
            );
        }

        return response.jsonPath().getString("message");
    }

    private void logResponse() {
        LogsManager.info("API status code: " + response.statusCode());
        LogsManager.info("API content type: " + response.contentType());
        LogsManager.info("API response body: " + response.asString());
    }
}
