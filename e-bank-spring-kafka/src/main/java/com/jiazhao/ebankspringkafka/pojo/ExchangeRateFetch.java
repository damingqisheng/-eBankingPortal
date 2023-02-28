package com.jiazhao.ebankspringkafka.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class ExchangeRateFetch implements Runnable{
    static String url = "https://openexchangerates.org/api/latest.json?app_id=6cdf74c461f940c7a8ed6efe9d660b43";
    @SneakyThrows
    @Override
    public void run() {

        while(true) {
            Thread.sleep(60 * 60 * 1000);
            JSONObject jsonObject = parser(url);
            JSONObject rates = jsonObject.getJSONObject("rates");
            double GBP = rates.getDouble("GBP");
            double EUR = rates.getDouble("EUR");
            double CHF = rates.getDouble("CHF");
            Constants.exchangeRates[0][1] = (1/GBP) / (1/EUR);
            Constants.exchangeRates[0][2] = (1/GBP) / (1/CHF);
            Constants.exchangeRates[1][0] = (1/EUR) / (1/GBP);
            Constants.exchangeRates[1][2] = (1/EUR) / (1/CHF);
            Constants.exchangeRates[2][0] = (1/CHF) / (1/GBP);
            Constants.exchangeRates[2][1] = (1/CHF) / (1/EUR);
            for(double[] i : Constants.exchangeRates) {
                System.out.println(Arrays.toString(i));
            }
            Thread.sleep(60 * 60 * 1000);
        }

    }

    public JSONObject parser(String request) throws IOException, JSONException {
        URL url = new URL(request);
        HttpURLConnection httpURLconnection = (HttpURLConnection) url.openConnection();
        httpURLconnection.setRequestMethod("GET");
        httpURLconnection.setConnectTimeout(5000);
        httpURLconnection.setReadTimeout(5000);
        httpURLconnection.addRequestProperty("Content-Type", "application/json");
        int status = httpURLconnection.getResponseCode();
        InputStream in = (status < 200 || status > 299) ?
                httpURLconnection.getErrorStream() : httpURLconnection.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String responseLine;
        StringBuffer responseContent = new StringBuffer();
        while((responseLine = br.readLine()) != null) {
            responseContent.append(responseLine);
        }
        br.close();

        JSONObject json = new JSONObject(responseContent.toString());
        return json;
    }


}
