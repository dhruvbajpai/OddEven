package com.dhruv.oddeven;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dhruv.oddeven.Utils.History_List;
import com.dhruv.oddeven.Utils.LoginManager;
import com.dhruv.oddeven.Utils.PostManager;

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

public class SignUp extends AppCompatActivity {

    Toolbar toolbar;
    String name;
    String age;
    String phone_no;
    String email;
    String password;
    String gender;
    String numberplate;
    String cartype;
    private RadioGroup radioSexGroup, radioCarTypeGroup;
    private RadioButton radioSexButton, radioCarButton;

    EditText p_name, p_email, p_password, p_phone_no, p_age, p_numberplate;
    TextView p_cartype;

    Button signup;

    public static String odd_car_string = "Odd Car";
    public static String evnve_car_string = "Even Car";
    public static String odd = "Odd";
    public static String even = "Even";

    int cart = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.app_bar_signup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("One Time Registration");
        initializestuff();

        facebookcheckFillDetailsSignup();

        signup = (Button) findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);

                int carId = radioCarTypeGroup.getCheckedRadioButtonId();
                radioCarButton = (RadioButton) findViewById(carId);


                gender = radioSexButton.getText().toString();
                name = p_name.getText().toString();
                age = p_age.getText().toString();
                phone_no = p_phone_no.getText().toString();
                email = p_email.getText().toString();
                password = p_password.getText().toString();
                numberplate = p_numberplate.getText().toString();
                cartype = radioCarButton.getText().toString();

                //if((name=="")||(age=="")||(phone_no=="")||(email=="")||(password=="")||(numberplate==""))
                if ((name == null) || (age == null) || (phone_no == null) || (email == null) || (password == null) || (numberplate == null))
                //if(!((name!=null)&&(age!=null)&&(phone_no!=null)&&(email!=null)&&(password!=null)&&(numberplate!=null)))//Something Is null
                {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                }

              /*  new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        //String query = "Select * from people where cartype ="+car_type_to_search;
                        String query = "Insert into people values(";
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://oddeven.freeoda.com/android_api/getPop.php");
                        try {
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                            nameValuePairs.add(new BasicNameValuePair("query", query));
                            nameValuePairs.add(new BasicNameValuePair("code", "safetyfirst"));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            String str = PostManager.inputStreamToString(response.getEntity().getContent()).toString();
                            String response_string = str;
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();*/

            }
        });
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioCarTypeGroup = (RadioGroup) findViewById(R.id.radiotype);


    }

    public void initializestuff() {
        p_name = (EditText) findViewById(R.id.input_name);
        p_email = (EditText) findViewById(R.id.input_email);
        p_password = (EditText) findViewById(R.id.input_password);
        p_age = (EditText) findViewById(R.id.input_age);
        //  p_cartype = (TextView) findViewById(R.id.car_type_here);
        p_phone_no = (EditText) findViewById(R.id.input_phone);
        p_numberplate = (EditText) findViewById(R.id.input_regno);

    }


    public void facebookcheckFillDetailsSignup() {




    }


}
