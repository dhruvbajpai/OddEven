package com.dhruv.oddeven;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detailed_Result extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    String id;
    String name;
    String gender;
    String email;
    String age;
    String cartype;
    String phone_no;
    String carnumber;
    String imageuri;
    String fromaddress;
    String toaddress;
    String gcm_regid;
    CircleImageView cv;
    TextView p_name, p_g_a, p_cartype, p_carnumber, p_email;
    Button call;
    TextView source_add, destination_add;
   public static ArrayList<Marker> marker_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        gender = i.getStringExtra("gender");
        age = i.getStringExtra("age");
        email = i.getStringExtra("email");
        phone_no = i.getStringExtra("phone_no");
        cartype = i.getStringExtra("cartype");
        carnumber = i.getStringExtra("carnumber");
        imageuri = i.getStringExtra("imageuri");
        fromaddress = i.getStringExtra("fromaddress");
        toaddress = i.getStringExtra("toaddress");
        gcm_regid = i.getStringExtra("gcm_regid");
        marker_list = new ArrayList<Marker>();

        setContentView(R.layout.activity_detailed__result);
        toolbar = (Toolbar) findViewById(R.id.app_bar_detailed_result);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ride Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        source_add = (TextView) findViewById(R.id.from_address);
        destination_add = (TextView) findViewById(R.id.to_address);
        source_add.setText(fromaddress);
        destination_add.setText(toaddress);
        call = (Button) findViewById(R.id.call_b);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });
        p_cartype = (TextView) findViewById(R.id.cartype);
        p_cartype.setText(cartype + "Car");
        p_email = (TextView) findViewById(R.id.person_email);
        p_email.setText(email);
        p_carnumber = (TextView) findViewById(R.id.carnumber);
        p_carnumber.setText(carnumber);
        p_g_a = (TextView) findViewById(R.id.gender_age);
        p_g_a.setText(gender + " / " + age);
        p_name = (TextView) findViewById(R.id.profile_name);
        p_name.setText(name);
        cv = (CircleImageView) findViewById(R.id.profile_image);
        Picasso.with(this).load(imageuri).into(cv);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
      //  Toast.makeText(this, phone_no, Toast.LENGTH_SHORT).show();

        //getLocationFromAddress(fromaddress);

    }


    public void onMapReady(final GoogleMap googleMap) {
        getLocationFromAddress(fromaddress, toaddress, googleMap);// TO generate markers and polylines

       /* LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected Void doInBackground(Void... params) {
               // getLocationFromAddress(fromaddress,toaddress,googleMap);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               // animate_bound(googleMap);

            }
        }.execute();

    }

    public void getLocationFromAddress(String strAddress, String desAddress, GoogleMap googleMap) {

        Geocoder coder = new Geocoder(this);
        List<Address> str_address = new ArrayList<>();
        List<Address> des_address = new ArrayList<>();
        //GeoPoint p1 = null;

        try {
            try {
                str_address = coder.getFromLocationName(strAddress, 5);
                des_address = coder.getFromLocationName(desAddress, 5);

            } catch (Exception e) {
                e.printStackTrace();
                ;
            }

            if ((str_address == null) || (des_address == null)) {
                Toast.makeText(getApplicationContext(), "ERROR IN MARKING", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"KUCH NHI HUA",Toast.LENGTH_SHORT).show();
                //return null;
            }
            Address str_location = str_address.get(0);
            Address des_location = des_address.get(0);
            LatLng str_latlngob = new LatLng(str_location.getLatitude(), str_location.getLongitude());
            LatLng des_latlngob = new LatLng(des_location.getLatitude(), des_location.getLongitude());

            // Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();

            Marker src = googleMap.addMarker(new MarkerOptions().position(str_latlngob).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            Marker des = googleMap.addMarker(new MarkerOptions().position(des_latlngob).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            marker_list.add(src);
            marker_list.add(des);

            LatLngBounds bounds = LatLngBounds.builder().include(str_latlngob).include(des_latlngob).build();
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,5));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(str_latlngob,9.0f));

            LatLng centre_location = googleMap.getCameraPosition().target;
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(centre_location.latitude + 0.0002f, centre_location.longitude + 0.0002f), 17.0f));

            //animate_bound(googleMap);
            //p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
            // (int) (location.getLongitude() * 1E6));

            //return p1;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void animate_bound(GoogleMap mMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : marker_list) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 15;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }
}
