package com.dhruv.oddeven.Utils;

import android.util.Log;

import com.dhruv.oddeven.Even_Result_Frag;
import com.dhruv.oddeven.Odd_Result_Frag;
import com.dhruv.oddeven.Search_Rides;

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

/**
 * Created by Dhruv on 27-Dec-15.
 */
public class PersonDownload {


    public static void downloadData(String car_type_to_search)
    {
       // ListPeople.people.clear();
        //Clear corrssponding lists for even and odd to avoid adding same results to lists
        if(car_type_to_search.equals("odd"))
        {
            Odd_Result_Frag.people_list.clear();
        }
        else if(car_type_to_search.equals("even"))
        {
            Even_Result_Frag.people_list.clear();
        }


        //String query = "Select * from people where cartype ="+car_type_to_search;
        String query = "";
        if(car_type_to_search.equals("odd"))
        {

            query = "Select * from people p, commutes c where c.source like '%" + Search_Rides.src+
                    "%' and c.destination like '%" + Search_Rides.des+
                    "%' and p.id = c.p_id and p.cartype='Odd' order by views desc";
        }
        else if(car_type_to_search.equals("even"))
        {
            query = "Select * from people p, commutes c where c.source like '%" + Search_Rides.src+
                    "%' and c.destination like '%" + Search_Rides.des+
                    "%' and p.id = c.p_id and p.cartype='Even' order by views desc";
        }
        //String query = "Select * from people";

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        //* login.php returns true if username and password is equal to saranga *//*
        //HttpPost httppost = new HttpPost("http://www.hayrom.tk/android_api/getPeople.php");
        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/getPop.php");

        Log.d("TAG", "Second");
        try {
            // Add user name and password
            Log.d("TAG","Third");
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
               for(int i=0;i<jsonarray.length();i++)
               {
                   JSONObject jobject = jsonarray.getJSONObject(i);

                    String id;
                    String name;
                    String age;
                    String email;
                    String gender;
                    String src_address;
                   String des_address;
                    String phone_no;
                    String cartype;
                    String numberplate;
                    String gcm_regid;


                   id = jobject.opt("id").toString();
                   name = jobject.opt("name").toString();
                   age = jobject.opt("age").toString();
                   email = jobject.opt("email").toString();
                   gender = jobject.opt("gender").toString();
                   src_address = jobject.opt("source").toString();
                   des_address = jobject.opt("destination").toString();
                   phone_no = jobject.opt("phone_no").toString();
                   cartype = jobject.opt("cartype").toString();
                   numberplate = jobject.opt("reg_no").toString();
                   gcm_regid = jobject.opt("gcm_regid").toString();


                   Person newperson = new Person(name,id,email,phone_no,gender,age,cartype,src_address,des_address,numberplate,gcm_regid);
                   //ListPeople.people.add(newperson);
                   if(car_type_to_search.equals("odd"))
                   {
                       Odd_Result_Frag.people_list.add(newperson);
                   }
                   else if(car_type_to_search.equals("even"))
                   {
                       Even_Result_Frag.people_list.add(newperson);
                   }


               }
//                ListPeople.mAdapter.notifyDataSetChanged();



                Log.d("RESPONSE IS",response_string);


                //JSONArray jsonArray = jsonObject.optJSONArray("user");

                //String name = .optString("name").toString();

                //Log.d("TAG","Name: "+name);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
