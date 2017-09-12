package com.noah.taxidriver;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by YH on 2017-08-25.
 */

public class register extends AppCompatActivity {

    EditText email;
    EditText pw1;
    EditText name1;
    EditText phone1;
    EditText car_num1;
 Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    email = (EditText)findViewById(R.id.editText2);
        pw1 = (EditText)findViewById(R.id.editText3);
        name1 = (EditText)findViewById(R.id.editText4);
        phone1 = (EditText)findViewById(R.id.editText5);
        car_num1 = (EditText)findViewById(R.id.editText6);
button = (Button)findViewById(R.id.button2);

//추가한 라인
        Log.i("login","시작");
        //결국 한 기기에선 똑같은 토큰이 나오게 되있음.
        final String token =  FirebaseInstanceId.getInstance().getToken();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String pw = pw1.getText().toString();
                String name = name1.getText().toString();
                String phone = phone1.getText().toString();
                String car_num = car_num1.getText().toString();
                Item_signup item_signup = new Item_signup(e,pw,token,phone,name,car_num);
       String send=  intro.gson.toJson(item_signup);
                trytologin(send);
                Log.i("register","json = "+send);

            }
        });
    }
    private void trytologin(final String send) {
        class checkEmail extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Response response;
            String get;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(register.this, "Please Wait", null, true, true); //서버접속하기 전에 로딩 띄우기
            Log.i("register","백그라운드 시작");
            }

            @Override
            protected void onPostExecute(String s) { //서버접속했다면 로딩끄기
                super.onPostExecute(s);
                Log.i("register_response",s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {
                //이부분에서 data를 key로  메시지를 보내준다.
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("data", send)
                        .build();

                //request
                Request request = new Request.Builder()
                        .url("http://teampinky.vps.phps.kr/API/register_driver.php")
                        .post(body)
                        .build();

                try {
                  response =   client.newCall(request).execute();






                    get = response.body().string();

                } catch (IOException error) {
                    error.printStackTrace();
                }

                return get;
            }
        }
        checkEmail task = new checkEmail();
        task.execute();
    }
}
