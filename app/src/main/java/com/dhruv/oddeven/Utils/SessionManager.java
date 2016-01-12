package com.dhruv.oddeven.Utils;

import java.util.HashMap;

/**
 * Created by Dhruv on 25-Dec-15.
 */
public class SessionManager {


    static int id;
    static String name;
    static String age;
    static String email;
    static String password;
    static String gender;
    static String address;
    static String phone_no;
    static String cartype;
    static String numberplate;
    static String gcm_regid;
    static boolean isLoggedIn = false;

    public static HashMap<String,String> user_details ;


    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    /*public static HashMap<String,String> logIn(String username,String password)
    {
        HashMap<String,String> logInMap = new HashMap<String,String>();


    }*/

}
