package com.dhruv.oddeven;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhruv.oddeven.Utils.DownloadHistory;
import com.dhruv.oddeven.Utils.History_List;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Sent_Ride extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;
    public static List<History_List> sent_list = new ArrayList<History_List>();

    public Sent_Ride() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sent__ride, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.sent_view);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
	//Toast.maketext(getApplicationContext().getActivity(),"DONE HERE",LENGTH.SHORT).show();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                DownloadHistory.downloadData("sent");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter = new RecyclerViewMaterialAdapter(new MyAdapter(getActivity().getApplicationContext(), sent_list));
                mRecyclerView.setAdapter(mAdapter);// to be put in onPOst
                //mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();


        return v;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        List<History_List> people = new ArrayList<History_List>();
        LayoutInflater inflater;
        Context context;


        public MyAdapter(Context context, List<History_List> people) {
            inflater = LayoutInflater.from(context);
            this.people = people;
            this.context = context;

        }

        class MyViewHolder extends RecyclerView.ViewHolder {



            String from_id;
            String from_name;
            String from_gcm_regid;
            String to_id;
            String to_name;
            String to_gcm_regid;
            String imageuri;

            TextView from_name_tv,time,addresse; ;

            CircleImageView person_image;



            public MyViewHolder(View itemView) {
                super(itemView);
                View v = itemView;

                addresse = (TextView) v.findViewById(R.id.adresse);
                from_name_tv = (TextView) v.findViewById(R.id.name_history);
                time = (TextView) v.findViewById(R.id.time_history);
                person_image  = (CircleImageView) v.findViewById(R.id.profile_image_history);

            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.history_card, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            final int pos = position;
            Log.d("TAG","HERE WE ARE");

            History_List current_person = people.get(position);
            Log.d("TAG",current_person.getFrom_name());

            holder.imageuri = "http://oddeven.freeoda.com/OddEven/people_images/"+current_person.getFrom_id()+".jpg";
            holder.from_name_tv.setText(current_person.getTo_name());
            holder.addresse.setText("TO: ");
            holder.time.setText(current_person.getTime());
            //Log.d("IMAGE HERE","IMAGE LOADED FROM ID : "+ DownloadHistory.getIdfromEmail(current_person.getFrom_id()));
            DownloadHistory.getIdfromEmail(context,current_person.getTo_id(),holder.person_image);
            //Picasso.with(context).load("http://oddeven.freeoda.com/OddEven/people_images/" + DownloadHistory.getIdfromEmail(context,current_person.getFrom_id()holder.person_image) + ".jpg").error(R.drawable.user_error).into(holder.person_image);

        }

        @Override
        public int getItemCount() {
            return people.size();
            //return 200;

        }


    }
}
