package com.dhruv.oddeven.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhruv.oddeven.R;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruv on 24-Dec-15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Person> people = new ArrayList<Person>();
    LayoutInflater inflater;
    Context context ;

    public MyAdapter(Context context, List<Person> people) {
        inflater = LayoutInflater.from(context);
        this.people = people;
        this.context = context;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button call;
        Button send_req;
        ImageView p_view;
        TextView p_name,p_cartype;
        TextView p_info;
        public MyViewHolder(View itemView) {
            super(itemView);
            p_cartype = (TextView) itemView.findViewById(R.id.person_cartype);
            p_info = (TextView) itemView.findViewById(R.id.person_info);
            p_name = (TextView) itemView.findViewById(R.id.person_name);
            call = (Button) itemView.findViewById(R.id.call);
            send_req = (Button) itemView.findViewById(R.id.sendRequest);
            p_view = (ImageView) itemView.findViewById(R.id.person_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.person_card,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final int pos = position;
       Person current_person = people.get(position);
        holder.p_cartype.setText(current_person.getCarType()+" car");
        //holder.p_cartype.setText("Odd"+" car");
        holder.p_info.setText(current_person.getGender()+" / "+current_person.getAge());
        //holder.p_info.setText("Male"+" / "+"21");
        holder.p_name.setText(current_person.getName().toString());
        //holder.p_name.setText("Dhruv Bajpai");
        Picasso.with(context).load("http://oddeven.freeoda.com/OddEven/people_images/"+String.valueOf(position+1)+".jpg").error(R.drawable.user_error).into(holder.p_view);
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

    /*public static class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(Button send, Button call)
        {
            super(send,call);
        }


        //public ViewHolder()

    }*/
}
