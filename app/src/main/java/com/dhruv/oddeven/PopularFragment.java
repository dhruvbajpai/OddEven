package com.dhruv.oddeven;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;


public class PopularFragment extends Fragment {

    Button open_people;
    Button search;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_popular, container, false);
        Button search  = (Button) v.findViewById(R.id.search_find);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),Search_Rides.class);
                startActivity(i);
            }
        });
        //open_people = (Button) v.findViewById(R.id.open_people_page);

        /*open_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),ListPeople.class);
                startActivity(i);
            }
        });*/

        return v;
    }





}
