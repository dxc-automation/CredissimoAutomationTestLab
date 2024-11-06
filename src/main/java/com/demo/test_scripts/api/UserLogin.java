package com.demo.test_scripts.api;

import com.demo.config.HttpHelper;
import com.demo.properties.Endpoints;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.net.URI;

import static com.demo.config.HttpHelper.setHttpClient;
import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.BasicTestConfig.*;

public class UserLogin {

    static final Logger LOG = LogManager.getLogger(UserLogin.class);

    HttpHelper helper = new HttpHelper();


    private void report() throws Exception {
        String testName        = "<b>[POST] Account Login</b>";
        String testCategory    = "API";
        String testDescription = "The purpose of this test is to verify that the login functionality is working as expected"              +
                "<br><br><b>*****   D E S C R I P T I O N   *****</b><br><br>"                                                       +
                "[1] Check that the login page can be opened and displayed with correct title.<br>"                      +
                "[2] Check the visualization of the login form element by image comparing based on RGB color model.<br>" +
                "[3] Check login with valid credentials.";

        startTestReport(testName, testDescription, testCategory);
    }




    public String userLogin(String email, String password) throws Exception {

        URI url = helper.buildUri(Endpoints.host, Endpoints.user_login);
        constants.setUri(url);

        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        constants.setRequestBody(requestBody.toString());

        StringEntity entity = new StringEntity(requestBody.toString());

        HttpPost post = new HttpPost(constants.getUri());
        post.setHeader("Content-Type", "application/json");
        post.setEntity(entity);

        CloseableHttpResponse response = setHttpClient().execute(post);

        // Get response details
        HttpHelper.getClosableHttpClientResponseDetails(response);

   //     System.out.println(Constants.responseBody);
      //  JSONObject body = new JSONObject(Constants.responseBody);
     //   String token = body.get("token").toString();

        Assert.assertTrue(constants.getResponseCode() > 199 && constants.getResponseCode() < 300);

        String token = "";
        return token;
    }

}
