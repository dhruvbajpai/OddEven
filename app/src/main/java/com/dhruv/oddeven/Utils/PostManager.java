package com.dhruv.oddeven.Utils;

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
 * Created by Dhruv on 26-Dec-15.
 */
public class PostManager {

    public static JSONObject jsonObject;
    public static JSONObject user_json_object;
    public static JSONObject error_json_object;

    public static boolean postLoginData(String username,String password) {

        Log.d("TAG","First");


         // Create a new HttpClient and Post Header
       HttpClient httpclient = new DefaultHttpClient();

        //* login.php returns true if username and password is equal to saranga *//*
        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/Login.php");

        Log.d("TAG","Second");
        try {
            // Add user name and password
            Log.d("TAG","Third");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            Log.w("SENCIDE", "Execute HTTP Post Request");
            HttpResponse response = httpclient.execute(httppost);

            String str = inputStreamToString(response.getEntity().getContent()).toString();

            Log.d("------------A---", str.toString());

          //  int c = str.indexOf("<!--");
            //String response_string = str.substring(0, c);
            String response_string = str;
            try {
                 jsonObject = new JSONObject(response_string);
                user_json_object = jsonObject.optJSONObject("user");

                //error_json_object = jsonObject.optJSONObject("error");

                Log.d("ERROR THING",jsonObject.optString("error"));

                if(jsonObject.optString("error").toString().equals("false")) {

                    Log.d("--TAG-USER KA NAME--",user_json_object.optString("name"));
                    Log.d("--TAG-USER KA EMAIL--",user_json_object.optString("email"));
                    Log.d("--TAG-USER KA EMAIL--",user_json_object.optString("gender"));
                    Log.d("--TAG-USER KA EMAIL--",user_json_object.optString("address"));
                    return true; // no error - logged in true
                }
                else
                    return false; // error not logged in

                //JSONArray jsonArray = jsonObject.optJSONArray("user");

                //String name = .optString("name").toString();

                //Log.d("TAG","Name: "+name);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "Post ended");
        return false;
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
                Log.d("STRING BUILD",line);
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
