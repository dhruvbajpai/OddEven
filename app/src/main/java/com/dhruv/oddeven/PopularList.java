package com.dhruv.oddeven;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.dhruv.oddeven.Utils.LoginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PopularList extends ActionBarActivity {

    Toolbar toolbar;
    Uri imageuri;
    ImageView iv;
    TextView tv;
    int button_counter = 0;

    public static String name;
    public static String email;
    public static String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);
        toolbar = (Toolbar) findViewById(R.id.app_bar_pop);
        iv = (ImageView) findViewById(R.id.image_uri);
        tv = (TextView) findViewById(R.id.fb_name);
//        Log.d("TAG", Profile.getCurrentProfile().getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular Routes");
        /*FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment pop_frag = new PopularFragment();
        transaction.add(R.id.main_container, pop_frag);
        transaction.commit();*/

        if ((LoginFragment.loginFrom == "facebook") || (Profile.getCurrentProfile() != null)) {// if its facebook login

            handleFacebookLogin();


        } else if (LoginFragment.loginFrom == "applogin") { // if its app login
            loginFromApp();
        }


        //  Log.d("TAG DETAILS",SessionManager.user_details.get("name"));
        // CODE FOR THE NAVIGATION DRAWER HERE ON THIS PAGE


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loginFromFacebook() {
        if (Profile.getCurrentProfile() != null)// check if facebook has been logged in once or not.
        {
            Profile profile = Profile.getCurrentProfile();
            name = profile.getName();
            // email = LoginFragment.email;
            Log.d("NAME FROM AC", Profile.getCurrentProfile().getName());

            // imageuri = profile.getProfilePictureUri(100,100);

            //        Picasso.with(this).load(imageuri).into(iv);
            //      tv.setText("Welcome "+Profile.getCurrentProfile().getName());
        }

    }


    public void loginFromApp() {
        name = LoginManager.name;
        email = LoginManager.email;
        esTablishNavigationDrawer();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment pop_frag = new PopularFragment();
        transaction.add(R.id.main_container, pop_frag);
        transaction.commit();


        Log.d("EMAIL OF THE PERSON", email);
        Log.d("THE NAME OF THE PERSON", name);
    }

    public void esTablishNavigationDrawer() {

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load("http://hayrom.tk/imgs/" + String.valueOf(1) + ".jpg").placeholder(placeholder).into(imageView);
                super.set(imageView, uri, placeholder);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this).withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email))

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem home = new PrimaryDrawerItem().withName("Home").withIcon(R.mipmap.ic_home_black_24dp);
        SecondaryDrawerItem findcommute = new SecondaryDrawerItem().withName("Find Rides").withIcon(R.mipmap.ic_search_black_48dp);
        SecondaryDrawerItem addcommute = new SecondaryDrawerItem().withName("Add a Route").withIcon(R.mipmap.carpool);
        SecondaryDrawerItem ridehistory = new SecondaryDrawerItem().withName("Ride Reqeust History").withIcon(R.mipmap.ic_assignment_black_48dp);
        SecondaryDrawerItem about_us = new SecondaryDrawerItem().withName("About Us").withIcon(R.mipmap.ic_info_black_48dp);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Settings").withIcon(R.mipmap.ic_settings_black_48dp);
        SecondaryDrawerItem rate_us = new SecondaryDrawerItem().withName("Rate Us").withIcon(R.mipmap.ic_thumb_up_black_48dp);
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withName("Logout").withIcon(R.mipmap.ic_backspace_black_48dp);
        SecondaryDrawerItem Ride_Route = new SecondaryDrawerItem().withName("Rides/Route");
        SecondaryDrawerItem myRoutes = new SecondaryDrawerItem().withName("My Routes").withIcon(R.mipmap.carpool);

//create the drawer and remember the `Drawer` result object
        final Drawer result = new DrawerBuilder().withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home,
                        new DividerDrawerItem(),
                        Ride_Route,
                        findcommute,
                        myRoutes,
                        addcommute,
                        ridehistory,
                        new DividerDrawerItem(),
                        settings,
                        about_us,
                        rate_us,
                        logout

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position) {

                            case 1://Home
                                break;

                            case 4://find rides
                                Intent i = new Intent(PopularList.this, Search_Rides.class);
                                startActivity(i);
                                break;
                            case 5:// myRoutes
                                break;
                            case 6://add commutes
                                Intent j = new Intent(PopularList.this, Add_Commute.class);
                                startActivity(j);
                                break;
                            case 7://Ride history
                                Intent iu = new Intent(PopularList.this, RideHistory.class);
                                startActivity(iu);
                                break;
                            case 8://line
                                break;
                            case 9://Settings
                                break;
                            case 10://About Us
                                break;
                            case 11://Rate us
                                break;


                            case 12://Logout
                                if ((LoginFragment.loginFrom == "facebook") || (Profile.getCurrentProfile() != null)) {
                                    com.facebook.login.LoginManager.getInstance().logOut();
                                } else if (LoginFragment.loginFrom == "applogin") {
                                    LoginFragment.loginFrom = "notloggedin";
                                    SharedPreferences.Editor editor = getSharedPreferences("loginPref", Context.MODE_PRIVATE).edit();
                                    editor.putBoolean("isLoggedIn", false);
                                    editor.putString("username", "null");
                                    editor.putString("password", "null");
                                    editor.commit();


                                }

                                finish();
                                break;

                        }
                        /*try {
                            result.closeDrawer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

                        return true;
                    }
                })
                .build();
        result.openDrawer();


        // CODE FOR THE NAVIGATION DRAWER HERE ON THIS PAGE

    }

    public void handleFacebookLogin() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            email = object.getString("email");
                            gender = object.getString("gender");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        loginFromFacebook();
                        LoginManager.name = Profile.getCurrentProfile().getName(); // Set this for global access
                        LoginManager.email = email;
                        esTablishNavigationDrawer();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment pop_frag = new PopularFragment();
                        transaction.add(R.id.main_container, pop_frag);
                        transaction.commit();

                        // uses the library functions to generate the NAVIGATIONBAR


                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }



}
