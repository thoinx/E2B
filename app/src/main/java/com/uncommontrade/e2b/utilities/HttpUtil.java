package com.uncommontrade.e2b.utilities;


import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class HttpUtil {
    private static int TIME_OUT = 30000;
    private static String TAG = "E2B";
    private static String DOMAIN = "http://app.vapecode.co/cpapi/";
    private static String APIKEY = "4a37e4605e43162740d818a88cf0ffdb";

    public static String httpGet(String subUrl, String authen) {
        String url = DOMAIN + subUrl;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");
            con.setConnectTimeout(TIME_OUT);
            con.setReadTimeout(TIME_OUT);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Apikey", APIKEY);
            if (!TextUtils.isEmpty(authen)) {
                con.setRequestProperty("Authorization-data", authen);
            }
            //add request header

            int responseCode = con.getResponseCode();
            L.d("Sending 'GET' request to URL : " + url);
            L.d("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            L.e(e.toString());
            return null;
        }
    }

    public static String httpPost(String subUrl, String urlParameters, String authen) {
        try {
            String url = DOMAIN + subUrl;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setConnectTimeout(TIME_OUT);
            con.setReadTimeout(TIME_OUT);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Apikey", APIKEY);
            if (!TextUtils.isEmpty(authen)) {
                con.setRequestProperty("Authorization-data", authen);
            }
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer .write(urlParameters);
            writer .flush();
            wr.close();

            int responseCode = con.getResponseCode();
            L.e("Sending 'POST' request to URL : " + url);
            L.e("Post parameters : " + urlParameters);
            L.e("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            L.e("Response json : " + response.toString());
            return response.toString();
        }catch (Exception e) {
            L.ee(e);
            return null;
        }
    }
}
