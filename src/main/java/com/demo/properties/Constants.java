package com.demo.properties;


import org.apache.http.HttpEntity;

import java.io.File;
import java.net.URI;


public class Constants {

    private int responseCode;
    private String responseBody;
    private String requestBody;
    private String responseHeaders;
    private String responseMsg;
    private HttpEntity httpEntity;
    private URI uri;

    private String actualImageName;
    private String expectedImageName;
    private String comparisonResultImage;
    private double comparisonDifference;



    public void setComparisonDifference(double comparisonDifference) {
        this.comparisonDifference = comparisonDifference;
    }

    public double getComparisonDifference() {
        return comparisonDifference;
    }

    public void setActualImageName(String actualImageName) {
        this.actualImageName = actualImageName;
    }

    public String getActualImageName() {
        return actualImageName;
    }

    public void setExpectedImageName(String expectedImageName) {
        this.expectedImageName = expectedImageName;
    }

    public String getExpectedImageName() {
        return expectedImageName;
    }

    public void setComparisonResultImage(String comparisonResultImage) {
        this.comparisonResultImage = comparisonResultImage;
    }

    public String getComparisonResultImage() {
        return comparisonResultImage;
    }


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }



    public static String token;
    public static String contactId;
}

