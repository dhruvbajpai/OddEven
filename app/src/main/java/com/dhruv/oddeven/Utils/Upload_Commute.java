package com.dhruv.oddeven.Utils;

import android.app.ProgressDialog;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruv on 31-Dec-15.
 */
public class Upload_Commute {

    public static JSONObject jsonObject;
    public static JSONObject user_json_object;
    public static JSONObject error_json_object;
    public static String id;

    public static void uploadit(String src, String des) {

        //if loggedin
        // change this ID value to the central global value
        id = LoginManager.id;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        //* login.php returns true if username and password is equal to saranga *//*
        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/OddEven/add_commute_android.php");

        try {
            // Add user name and password
            Log.d("TAG", "Third");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("source", src));
            nameValuePairs.add(new BasicNameValuePair("destination", des));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            Log.w("SENCIDE", "Execute HTTP Post Request");

            HttpResponse response = httpclient.execute(httppost);

            String str = inputStreamToString(response.getEntity().getContent()).toString();

            Log.d("TAGME",str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public static StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end

        Log.d("TAG", "InputReader Started");
        try {
            while ((line = rd.readLine()) != null) {
                Log.d("STRING BUILD", line);
                total.append(line);
            }
            Log.d("TAG", "InputReader finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return full string
        return total;
    }

}
