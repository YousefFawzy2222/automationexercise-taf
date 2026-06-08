package com.automationexercise.utils.actions.apis;

import com.automationexercise.utils.actions.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static final String baseUri = PropertyReader.getProperty("baseUrlApi");

    private Builder() {
        // Private constructor to prevent instantiation
    }

    public static RequestSpecification getUserManagemenetRequestSpecification(Map<String, ?> formParams){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }



}
