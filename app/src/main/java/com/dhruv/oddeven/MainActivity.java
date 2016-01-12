package com.dhruv.oddeven;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dhruv.oddeven.Utils.LoginManager;
import com.facebook.Profile;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import splashscrren.FirstPage;
import splashscrren.Second_Page;


public class MainActivity extends AppIntro {

    public static Boolean isSkipPressed = false;
    public static Boolean mainisloggedinwithapp = false;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(Profile.getCurrentProfile()!=null)
        {
            Intent i = new Intent(MainActivity.this,PopularList.class);
            startActivity(i);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment login = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container,login,"FirstLogin");
        fragmentTransaction.commit();

    }*/

    @Override
    public void init(Bundle savedInstanceState) {

//    setFeatureDrawable(R.drawable.startwallpaper,getResources().getDrawable(R.drawable.startwallpaper));

       /* if(Profile.getCurrentProfile()!=null)
        {
            Intent i = new Intent(MainActivity.this,PopularList.class);
            startActivity(i);

        }
        checkLoggedInandLogin();
*/

        //addSlide(AppIntroFragment.newInstance("FIRST PAGE", "DESCRIPTION", R.drawable.car, Color.GREEN));
        //addSlide(AppIntroFragment.newInstance("FIRST PAGE", "DESCRIPTION", R.drawable.car1, Color.RED));
        if(!isSkipPressed) {
            addSlide(new FirstPage());
            addSlide(new Second_Page());
            addSlide(new LoginFragment());
        }else
        {
            addSlide(new LoginFragment());
        }

        showSkipButton(true);
        setProgressButtonEnabled(false);
       // setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
        setFadeAnimation();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment login = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container,login,"FirstLogin");
        fragmentTransaction.commit();*/

    }

    @Override
    public void onSkipPressed() {
        MainActivity.isSkipPressed = true;
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

    }

    @Override
    public void onSlideChanged() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkLoggedInandLogin()
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
                    Log.d("USERNAME ENTERED",username);
                    Log.d("PASSWORD ENTERED",password);
                    mainisloggedinwithapp = LoginManager.LogIn(username, password);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent i = new Intent(MainActivity.this,PopularList.class);
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
}
