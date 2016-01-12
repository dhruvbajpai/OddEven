package com.dhruv.oddeven;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhruv.oddeven.Utils.Upload_Commute;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class Add_Commute extends AppCompatActivity {

    Toolbar toolbar;
    EditText source, destination;
    Button add_commute;

    String destination_from_google;
    String source_from_google;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // if logged in then get Id and show this screen... else show a screen.. " PLease login to add routes"
        setContentView(R.layout.activity_add__commute);
        toolbar = (Toolbar) findViewById(R.id.app_bar_add_commute);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Routes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // source = (EditText) findViewById(R.id.add_src_point);
        //destination = (EditText) findViewById(R.id.add_des_point);
        add_commute = (Button) findViewById(R.id.add_button);
        add_commute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((source_from_google != null) && (destination_from_google != null)) {


                    new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                        }

                        @Override
                        protected Void doInBackground(Void... params) {

                            Upload_Commute.uploadit(source_from_google,destination_from_google);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                        }
                    }.execute();
                }
                else
                {

                }
            }
        });

        PlaceAutocompleteFragment source_autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_src);
        source_autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                source_from_google = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {

            }
        });
        PlaceAutocompleteFragment dest_autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        dest_autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("TAG", "Place: " + place.getName());
                Toast.makeText(getApplicationContext(), place.getAddress().toString(), Toast.LENGTH_SHORT).show();
                destination_from_google = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                Log.i("TAG", "An error occurred: " + status);
            }
        });

       /* mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/


    }
/*
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }*/
}
