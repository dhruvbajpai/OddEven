package com.dhruv.oddeven;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.facebook.Profile;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.dhruv.oddeven.Utils.*;


public class ListPeople extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Toolbar toolbar;
    int back_counter =0;
   public static RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
   public static List<Person> people = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_people);
        //getSupportActionBar().hide();
        toolbar = (Toolbar) findViewById(R.id.app_bar_people);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Rides List");
        mRecyclerView = (RecyclerView) findViewById(R.id.people_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*people.add(new Person("Dhruv", "Male", 1));
        people.add(new Person("Rahul", "Male", 1));
        people.add(new Person("Mohit","Male",1));
        people.add(new Person("Vivek","Male",0));
        people.add(new Person("Harshita","Female",0));*/

        new AsyncTask<Void,Void,Void>()
        {

            ProgressDialog dialog ;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(getApplicationContext());
                dialog.setTitle("Matching Profile");
                dialog.setMessage("Downloading");
//                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {

            PersonDownload.downloadData("odd");

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ListPeople.mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }.execute();


        mAdapter = new MyAdapter(getApplicationContext(),people);
        mRecyclerView.setAdapter(mAdapter);
        esTablishNavigationDrawer();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(back_counter==0)
        {
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
        }
        else if(back_counter==1)
        {
            finish();
        }
        back_counter++;

    }

    public void esTablishNavigationDrawer()
    {

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
                        new ProfileDrawerItem().withName(PopularList.name).withEmail(PopularList.email))

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem home = new PrimaryDrawerItem().withName("Home").withIcon(R.mipmap.ic_home_black_24dp);
        SecondaryDrawerItem findcommute= new SecondaryDrawerItem().withName("Find Rides").withIcon(R.mipmap.ic_search_black_48dp);
        SecondaryDrawerItem addcommute= new SecondaryDrawerItem().withName("Add a Commute").withIcon(R.mipmap.carpool);
        SecondaryDrawerItem ridehistory = new SecondaryDrawerItem().withName("Ride Reqeust History").withIcon(R.mipmap.ic_assignment_black_48dp);
        SecondaryDrawerItem about_us = new SecondaryDrawerItem().withName("About Us").withIcon(R.mipmap.ic_info_black_48dp);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Settings").withIcon(R.mipmap.ic_settings_black_48dp);
        SecondaryDrawerItem rate_us = new SecondaryDrawerItem().withName("Rate Us").withIcon(R.mipmap.ic_thumb_up_black_48dp);
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withName("Logout").withIcon(R.mipmap.ic_backspace_black_48dp);

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder().withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home,
                        new DividerDrawerItem(),
                        findcommute,
                        addcommute,
                        ridehistory,
                        new DividerDrawerItem(),
                        settings,
                        about_us,
                        rate_us,
                        new DividerDrawerItem(),
                        logout

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position) {

                            case 1://Home
                                break;

                            case 3://find rides
                                break;
                            case 4://add commutes
                                break;
                            case 5://Ride history
                                break;
                            case 6://line
                                break;
                            case 7://Settings
                                break;
                            case 8://About Us
                                break;
                            case 9://Rate us
                                break;


                            case 11:
                                if ((LoginFragment.loginFrom == "facebook") || (Profile.getCurrentProfile() != null)) {
                                    com.facebook.login.LoginManager.getInstance().logOut();
                                } else if (LoginFragment.loginFrom == "applogin") {

                                }

                                finish();
                                break;

                        }
                        return true;
                    }
                })
                .build();
        //result.openDrawer();



        // CODE FOR THE NAVIGATION DRAWER HERE ON THIS PAGE

    }


}
