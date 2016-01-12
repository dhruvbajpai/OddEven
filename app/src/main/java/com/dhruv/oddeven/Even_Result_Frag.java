package com.dhruv.oddeven;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.dhruv.oddeven.Utils.Person;
import com.dhruv.oddeven.Utils.PersonDownload;


public class Even_Result_Frag extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;
    public static List<Person> people_list = new ArrayList<Person>();

    public Even_Result_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_even__result_, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.even_recyclerView);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                PersonDownload.downloadData("even");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter = new RecyclerViewMaterialAdapter(new MyAdapter(getActivity().getApplicationContext(), people_list));
                mRecyclerView.setAdapter(mAdapter);// to be put in onPOst
                MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();


        return v;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        List<Person> people = new ArrayList<Person>();
        LayoutInflater inflater;
        Context context;


        public MyAdapter(Context context, List<Person> people) {
            inflater = LayoutInflater.from(context);
            this.people = people;
            this.context = context;

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            String id;
            String name;
            String email;
            String phone_no;
            String gender;
            String age;
            String cartype;
            String carnumber;
            String imageuri;
            String fromaddress;
            String toaddress;
            String gcm_regid;
            Button call;
            Button send_req;
            ImageView p_view;
            TextView p_name, p_cartype;
            TextView p_info,p_from, p_to;



            public MyViewHolder(View itemView) {
                super(itemView);
                View v = itemView;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(),Detailed_Result.class);
                        i.putExtra("id",id);
                        i.putExtra("name",name);
                        i.putExtra("email",email);
                        i.putExtra("phone_no",phone_no);
                        i.putExtra("gender",gender);
                        i.putExtra("age",age);
                        i.putExtra("cartype",cartype);
                        i.putExtra("carnumber",carnumber);
                        i.putExtra("imageuri",imageuri);
                        i.putExtra("fromaddress",fromaddress);
                        i.putExtra("toaddress",toaddress);
                        i.putExtra("gcm_regid",gcm_regid);
                        startActivity(i);
                                            }
                });
                p_cartype = (TextView) itemView.findViewById(R.id.person_cartype);
                p_info = (TextView) itemView.findViewById(R.id.person_info);
                p_name = (TextView) itemView.findViewById(R.id.person_name);
                call = (Button) itemView.findViewById(R.id.call);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        startActivity(callIntent);
                    }
                });
                send_req = (Button) itemView.findViewById(R.id.sendRequest);
                p_view = (ImageView) itemView.findViewById(R.id.person_image);
                p_from = (TextView) itemView.findViewById(R.id.p_from);
                p_to = (TextView) itemView.findViewById(R.id.p_to);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.person_card, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            final int pos = position;
            Person current_person = people.get(position);
            holder.id = current_person.getId();
            holder.name = current_person.getName();
            holder.email = current_person.getEmail();
            holder.phone_no = current_person.getPhone_no();
            holder.gender = current_person.getGender();
            holder.fromaddress = current_person.getSourceAddress();
            holder.toaddress = current_person.getDestinationAddress();
            holder.age = current_person.getAge();
            holder.carnumber = current_person.getNumberPlate();
            holder.cartype = current_person.getCarType();
            holder.imageuri = "http://oddeven.freeoda.com/OddEven/people_images/"+current_person.getId()+".jpg";
            holder.p_cartype.setText(current_person.getCarType()+" car");
            holder.gcm_regid = current_person.getGcm_regid();
            holder.p_cartype.setText(current_person.getCarType() + " car");
            //holder.p_cartype.setText("Odd"+" car");
            holder.p_info.setText(current_person.getGender() + " / " + current_person.getAge());
            //holder.p_info.setText("Male"+" / "+"21");
            holder.p_name.setText(current_person.getName().toString());
            holder.p_from.setText(current_person.getSourceAddress());
            holder.p_to.setText(current_person.getDestinationAddress());
            //holder.p_name.setText("Dhruv Bajpai");
            Picasso.with(context).load("http://oddeven.freeoda.com/OddEven/people_images/" + current_person.getId() + ".jpg").error(R.drawable.user_error).into(holder.p_view);
            //Picasso.with(context).load("http://hayrom.tk/imgs/"+String.valueOf(1)+".jpg").error(R.drawable.user_error).into(holder.p_view);
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "CAll at" + pos + "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return people.size();
            //return 200;

        }


    }
}
