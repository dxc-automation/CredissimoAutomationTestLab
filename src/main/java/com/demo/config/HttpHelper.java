package com.demo.config;

import com.demo.properties.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static com.demo.config.BasicTestConfig.constants;

public class HttpHelper {

    public static CloseableHttpClient client;
    public static CookieStore cookieStore = new BasicCookieStore();


    public URI buildUri(String host, String path) throws URISyntaxException {
        return new URIBuilder()
                .setScheme("https")
                .setHost(host)
                .setPath(path)
                .build();
    }


    public static void getClosableHttpClientResponseDetails(CloseableHttpResponse response) throws Exception {
        try {
            int code = response.getStatusLine().getStatusCode();
            constants.setResponseCode(code);

            HttpEntity httpEntity = response.getEntity();
            constants.setHttpEntity(httpEntity);

            String responseBody = EntityUtils.toString(httpEntity, "UTF-8");
            constants.setResponseBody(responseBody);

            String responseMsg = response.getStatusLine().getReasonPhrase();
            constants.setResponseMsg(responseMsg);


            String responseHeaders = Arrays.asList(response.getAllHeaders())
                    .toString()
                    .replace(", ", "\n")
                    .replace("[", "")
                    .replace("]", "");
            constants.setResponseHeaders(responseHeaders);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getFormattedJson(String responseBody) {
        try {
            Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject    = parser.parse(responseBody).getAsJsonObject();
            String formattedJson = gson.toJson(jsonObject);
            return formattedJson;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static CloseableHttpClient setHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(300000)
                .setConnectTimeout(300000)
                .setConnectionRequestTimeout(300000)
                .setRedirectsEnabled(true)
                .build();

        return  client = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(config)
                .build();
    }
}
