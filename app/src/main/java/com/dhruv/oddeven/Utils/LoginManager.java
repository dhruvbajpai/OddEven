package com.dhruv.oddeven.Utils;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Dhruv on 25-Dec-15.
 */
public class LoginManager {


   public static String id;
   public static String name;
   public  static String age;
   public static String email;
    public static String password_d;
    public static String gender;
    public static String address;
    public static String phone_no;
    public static String cartype;
    public static String numberplate;
    public static String gcm_regid;
    public static boolean isLoggedIn = false;
    public static HashMap<String,String> u_details = new HashMap<String,String>();

    public static boolean LogIn(String username, String password)
    {


        isLoggedIn = PostManager.postLoginData(username,password);
        if(isLoggedIn)
        {
            try {
                id = PostManager.user_json_object.optString("id");
                name = PostManager.user_json_object.optString("name");
                age = PostManager.user_json_object.opt("age").toString();
                email = PostManager.user_json_object.optString("email");
                password_d = PostManager.user_json_object.optString("password");
                gender = PostManager.user_json_object.optString("gender");
                address = PostManager.user_json_object.optString("address");
                phone_no = PostManager.user_json_object.optString("phone_no");
                cartype = PostManager.user_json_object.optString("cartype");
                numberplate = PostManager.user_json_object.optString("reg_no");
                gcm_regid = PostManager.user_json_object.optString("gcm_regid");


                Log.d("TAG YAHA HONA CHAHIYE","Name: " + name+" Email: "+ email);
                u_details.put("id",id);
                u_details.put("name",name);
                u_details.put("age",age);
                u_details.put("email",email);
                u_details.put("password",password_d);
                u_details.put("gender",gender);
                u_details.put("address",address);
                u_details.put("phone_no",phone_no);
                u_details.put("cartype",cartype);
                u_details.put("numberplate",numberplate);
                u_details.put("gcm_regid",gcm_regid);

                SessionManager.user_details = u_details;
                SessionManager.isLoggedIn = true;



            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }

/*
        u_details.put("id",String.valueOf(id));
        u_details.put("name",name);
        u_details.put("age",age);
        u_details.put("email",email);
        u_details.put("password",LoginManager.password);
        u_details.put("gender",gender);
        u_details.put("address",address);
        u_details.put("phone_no",phone_no);
        u_details.put("numberplate",numberplate);

        isLoggedIn = true;*/
        Log.d("TAG","Loggeed in value"+String.valueOf(isLoggedIn));
        return isLoggedIn;
    }

    public static HashMap<String,String> getUserDetails()
    {

        return u_details;

    }

}
