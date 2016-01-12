package com.dhruv.oddeven.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dhruv.oddeven.Even_Result_Frag;
import com.dhruv.oddeven.Odd_Result_Frag;
import com.dhruv.oddeven.R;
import com.dhruv.oddeven.Received_rides;
import com.dhruv.oddeven.Search_Rides;
import com.dhruv.oddeven.Sent_Ride;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhruv on 27-Dec-15.
 */
public class DownloadHistory {


    public static void downloadData(String request_type) {
        // ListPeople.people.clear();
        //Clear corrssponding lists for even and odd to avoid adding same results to lists
        if (request_type.equals("received")) {
            Received_rides.received_list.clear();
        } else if (request_type.equals("sent")) {
            Sent_Ride.sent_list.clear();
        }


        //String query = "Select * from people where cartype ="+car_type_to_search;
        String query = "";
        if (request_type.equals("received")) {

            query = "Select * from ridehistory where to_id='" + LoginManager.email + "'";
            /*query = "Select * from people p, commutes c where c.source like '%" + Search_Rides.src+
                    "%' and c.destination like '%" + Search_Rides.des+
                    "%' and p.id = c.p_id and p.cartype='Odd' order by views desc";*/
        } else if (request_type.equals("sent")) {
            query = "Select * from ridehistory where from_id='" + LoginManager.email + "'";

        }


        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        //* login.php returns true if username and password is equal to saranga *//*
        //HttpPost httppost = new HttpPost("http://www.hayrom.tk/android_api/getPeople.php");
        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/getPop.php");

        Log.d("TAG", "Second");
        try {
            // Add user name and password
            Log.d("TAG", "Third");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("query", query));
            nameValuePairs.add(new BasicNameValuePair("code", "safetyfirst"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            Log.w("SENCIDE", "Execute HTTP Post Request");
            HttpResponse response = httpclient.execute(httppost);

            String str = PostManager.inputStreamToString(response.getEntity().getContent()).toString();
            //   Log.d("TAGME", "Second");

            // Log.d("REQUIRED STRING", str);

            /*if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();

                Log.d("TAGME",EntityUtils.toString(entity));
            }*/
//            Log.d("TAGME",response.getEntity().getContent().toString());
            //Log.d("TAGME",String.valueOf(response.getStatusLine().getStatusCode()));
            //Log.d("TAGME", response.toString());
            //Log.d("TAGME", "SECONDWRAP");

            String response_string = str;
            //int c = str.indexOf("<!--");
            //String response_string = str.substring(0, c);
            try {
//                jsonObject = new JSONObject(response_string);
                //              user_json_object = jsonObject.optJSONObject("user");
                //error_json_object = jsonObject.optJSONObject("error");

                JSONArray jsonarray = new JSONArray(response_string);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jobject = jsonarray.getJSONObject(i);


                    String from_id;
                    String from_name;
                    String from_gcm_regid;
                    String to_id;
                    String to_name;
                    String to_gcm_regid;
                    String time;


                    from_id = jobject.opt("from_id").toString();
                    from_name = jobject.opt("from_name").toString();
                    from_gcm_regid = jobject.opt("from_gcm_regid").toString();
                    to_id = jobject.opt("to_id").toString();
                    to_name = jobject.opt("to_name").toString();
                    to_gcm_regid = jobject.opt("to_gcm_regid").toString();
                    time = jobject.opt("time").toString();



                    History_List newperson = new History_List(from_id,from_name,from_gcm_regid,to_id,to_name,to_gcm_regid,time);
                    //ListPeople.people.add(newperson);
                    if (request_type.equals("received")) {
                        Received_rides.received_list.add(newperson);
                        Log.d("HISTORY","ADDED PERSON "+ newperson.from_name);
                    } else if (request_type.equals("sent")) {
                        Sent_Ride.sent_list.add(newperson);
                    }


                }
//                ListPeople.mAdapter.notifyDataSetChanged();


                Log.d("RESPONSE IS", response_string);


                //JSONArray jsonArray = jsonObject.optJSONArray("user");

                //String name = .optString("name").toString();

                //Log.d("TAG","Name: "+name);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String getIdfromEmail(Context context, String email, CircleImageView person_image)
    {
        final String myemail = email;
        final String[] id = {""};
        final String[] myid = {""};
        final Context c = context;
        final CircleImageView p  = person_image;

        new AsyncTask<Void,Void,Void>()
        {

            JSONObject jsonObject;
            @Override
            protected Void doInBackground(Void... params) {

                HttpClient httpclient = new DefaultHttpClient();

                //* login.php returns true if username and password is equal to saranga *//*
                HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/getPop.php");

                String query = "Select * from people where email ='"+ myemail+"'";

                Log.d("TAG","Second");
                try {
                    // Add user name and password
                    Log.d("TAG", "Third");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("query", query));
                    nameValuePairs.add(new BasicNameValuePair("code", ""));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



                    HttpResponse response = httpclient.execute(httppost);

                    String str = PostManager.inputStreamToString(response.getEntity().getContent()).toString();

                    String response_string = str;
                    try {



                        jsonObject = new JSONArray(response_string).getJSONObject(0);
                        JSONObject user_json_object = jsonObject.optJSONObject("user");


                        Log.d("TAG","HERE");
                        Log.d("TAG",myid[0]);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "Post ended");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                myid[0] =  jsonObject.opt("id").toString();
                Picasso.with(c).load("http://oddeven.freeoda.com/OddEven/people_images/" + myid[0] + ".jpg").error(R.drawable.user_error).into(p);
            }
        }.execute();
       // Picasso.with(context).load("http://oddeven.freeoda.com/OddEven/people_images/" + myid[0] + ".jpg").error(R.drawable.user_error).into(person_image);

        Log.d("RETURN VALLUE",myid[0]);
        return myid[0];
    }

}
