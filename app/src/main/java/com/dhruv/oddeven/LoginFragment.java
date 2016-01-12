package com.dhruv.oddeven;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.rey.material.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dhruv.oddeven.Utils.LoginManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button signin;
    Button signup;
    EditText username;
    EditText password;
    public static String email = "Null value";
    public static String gender = "null";
    public static AccessToken accessTk;
    ProgressDialog p_dialog;//= new ProgressDialog(getActivity().getApplicationContext());
    public static String loginFrom;

    // FacebookCallback tells us the result of the login process
    private CallbackManager mCallBackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {


        private ProfileTracker mProfileTracker;

        @Override
        public void onSuccess(LoginResult loginResult) {

            accessTk = loginResult.getAccessToken();
            //    Profile profile = Profile.getCurrentProfile();

            if (Profile.getCurrentProfile() == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Log.d("NEW PROFILE TRACKER", currentProfile.getName());
                        mProfileTracker.stopTracking();

                        Profile.setCurrentProfile(currentProfile);
                    }
                };
                mProfileTracker.startTracking();
            } else {
                Profile profile = Profile.getCurrentProfile();
                Log.d("NOT NULL NAME", profile.getName());
            }

            p_dialog = new ProgressDialog(getActivity().getApplicationContext());
            p_dialog.setMessage("Loading data from Facebook");
            p_dialog.setTitle("Authorizing");
//            p_dialog.show();
            //-----------------------------------------GET USER DATA-------------------------------------------------
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            try {
                                email = object.getString("email");
                                gender = object.getString("gender");
                                Toast.makeText(getActivity().getApplicationContext(), email, Toast.LENGTH_SHORT).show();
                                loginFrom = "facebook";
                                checkIDandRedirect(email);
                                //   p_dialog.dismiss();
                                Log.d("EMAIL", email);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.v("LoginActivity", response.toString());
                            loginFrom = "facebook";
                            //  Toast.makeText(getActivity().getApplicationContext(),"Welcome "+ profile.getName(),Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity().getApplicationContext(), PopularList.class);
                            //  startActivity(i);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);

            request.executeAsync();
            //-----------------------------------------GET USER DATA-------------------------------------------------

//            Log.d("PROFILE THINGS",profile.getName().toString());
            loginFrom = "facebook";
            //  Toast.makeText(getActivity().getApplicationContext(),"Welcome "+ profile.getName(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity().getApplicationContext(), PopularList.class);
            // startActivity(i);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    // LOGIN WITH FACEBOOK FOR LOGGING IN


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallBackManager = CallbackManager.Factory.create(); // CALL BACK MANAGER FOR FACEBOOK LOGIN

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //FacebookSdk.sdkInitialize(getActivity());
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) v.findViewById(R.id.username);
        password = (EditText) v.findViewById(R.id.password);
        signin = (Button) v.findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"SIGN IN",Toast.LENGTH_SHORT).show();

                final String user = username.getText().toString();
                final String pass = password.getText().toString();

                new AsyncTask<Void, Void, Void>() {
                    ProgressDialog p;
                    Boolean isLoggedIn;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        p = new ProgressDialog(getActivity());
                        p.setMessage("Logging In");
                        p.setCanceledOnTouchOutside(false);
                        p.show();

                    }

                    @Override
                    protected Void doInBackground(Void... params) {

                        isLoggedIn = LoginManager.LogIn(user, pass);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        p.dismiss();
                        if (isLoggedIn) {
                            Toast.makeText(getActivity(), "Logged In " + LoginManager.name, Toast.LENGTH_SHORT).show();
                            loginFrom = "applogin";
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("loginPref", Context.MODE_PRIVATE).edit();
                            editor.putBoolean("isLoggedIn", true);

                            editor.putString("username", LoginManager.email);
                            editor.putString("password", LoginManager.password_d);
                            editor.commit();
                            Intent i = new Intent(getActivity().getApplicationContext(), PopularList.class);
                            startActivity(i);
                            //save login data to shared preferences by calling a method of SessionManager
                        } else {
                            Toast.makeText(getActivity(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }


                    }
                }.execute();

            }
        });

        signup = (Button) v.findViewById(R.id.signup);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //----------------FACEBOOK LOGIN BUTTON-------------------------------
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, mCallback);
        //----------------FACEBOOK LOGIN BUTTON-------------------------------
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void checkIDandRedirect(final String email) {

        final String[] response_from_server = new String[1];

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getActivity().getApplicationContext(), "Authorizing", Toast.LENGTH_SHORT).show();
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
                    Intent i = new Intent(getActivity().getApplicationContext(), SignUp.class);
                    //  p_dialog.dismiss();
                    startActivity(i);
                } else if (response_from_server[0].equals("success")) {
                    Intent u = new Intent(getActivity().getApplicationContext(), PopularList.class);
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



