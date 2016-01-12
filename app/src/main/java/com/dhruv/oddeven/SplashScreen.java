package com.dhruv.oddeven;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dhruv.oddeven.Utils.LoginManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

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

public class SplashScreen extends AppCompatActivity {

    public static String email;

    public static Boolean mainisloggedinwithapp = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(Profile.getCurrentProfile()!=null)                               // FACEBOOK ALREADY LOGGED IN -- CHECK SERVER DATA EXISTS AND REDIRECT TO SIGNUP
                                                                            // OR POPULAR LISTS
        {
            /*Intent i = new Intent(SplashScreen.this,PopularList.class);
            startActivity(i);*/
            handleFacebookLogin();

        }
        else if(ischeckSessionfromAppLoggedIn()) {
            checkLoggedInandLogin();
        }
        else
        {
            Intent i = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(i);
        }




    }


    public void checkLoggedInandLogin() // handles normal logged in session
    {
        //SharedPreferences sharedpreferences = getSharedPreferences("loginpreferences", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();

        SharedPreferences prefs = getSharedPreferences("loginPref", MODE_PRIVATE);
        boolean isloggedin = prefs.getBoolean("isLoggedIn", false);
        final String username = prefs.getString("username", "null");
        final String password = prefs.getString("password","null");

        if(isloggedin)
        {   // If its logged in.
            //Download all data and push to PopularListActivity
            new AsyncTask<Void,Void,Void>()
            {

                @Override
                protected Void doInBackground(Void... params) {
                    Log.d("USERNAME ENTERED", username);
                    Log.d("PASSWORD ENTERED",password);
                    mainisloggedinwithapp = LoginManager.LogIn(username, password);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent i = new Intent(SplashScreen.this,PopularList.class);
                    Log.d("TAG",LoginManager.name);
                    Log.d("TAG",LoginManager.password_d);
                    Log.d("HEREREACHED","HereReached");
                    LoginFragment.loginFrom="applogin";
                    startActivity(i);
                }
            }.execute();
        }

        //String
        //editor.commit();
    }

    public boolean ischeckSessionfromAppLoggedIn()// checks app Login previously
    {
        SharedPreferences prefs = getSharedPreferences("loginPref", MODE_PRIVATE);
        boolean isloggedin = prefs.getBoolean("isLoggedIn", false);
        return isloggedin;

    }

    public void handleFacebookLogin()
    {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            email = object.getString("email");
                            //gender = object.getString("gender");
                            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
                            LoginFragment.loginFrom = "facebook";
                            checkIDandRedirect(email);
                            //   p_dialog.dismiss();
                            Log.d("EMAIL", email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.v("LoginActivity", response.toString());
                        LoginFragment.loginFrom = "facebook";
                        //  Toast.makeText(getActivity().getApplicationContext(),"Welcome "+ profile.getName(),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), PopularList.class);
                        //  startActivity(i);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);

        request.executeAsync();
    }

    public void checkIDandRedirect(final String email) {

        final String[] response_from_server = new String[1];

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getApplicationContext(), "Authorizing", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(Void... params) {

                response_from_server[0] = checkifUserExists(email);
                Log.d("TAG", "Response is: " + response_from_server[0].toString());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                if (response_from_server[0].equals("failure")) {
                    Intent i = new Intent(SplashScreen.this, SignUp.class);
                    //  p_dialog.dismiss();
                    startActivity(i);
                } else if (response_from_server[0].equals("success")) {
                    Intent u = new Intent(SplashScreen.this, PopularList.class);
                    // p_dialog.dismiss();
                    startActivity(u);
                }
            }
        }.execute();


    }

    public String checkifUserExists(String email) {
        HttpClient httpclient = new DefaultHttpClient();
        //* login.php returns true if username and password is equal to saranga *//*
        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/exist.php");
        String response_string = "";

        try {
            // Add user name and password
            Log.d("TAG", "Third");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            Log.w("SENCIDE", "Execute HTTP Post Request");
            HttpResponse response = httpclient.execute(httppost);

            response_string = inputStreamToString(response.getEntity().getContent()).toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response_string;


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
