package com.noah.taxidriver.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.noah.taxidriver.Item_signup;
import com.noah.taxidriver.R;
import com.noah.taxidriver.item_response;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by YH on 2017-08-25.
 */

public class Register_Activity extends Activity {

    EditText name;
    Button button;

    String driver_name;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gson = new Gson();

        pref = getSharedPreferences("Driver",MODE_PRIVATE);
        editor=pref.edit();

        name = (EditText) findViewById(R.id.register_driver);
        button = (Button) findViewById(R.id.register_join);

//추가한 라인
        //결국 한 기기에선 똑같은 토큰이 나오게 되있음.
        final String token = FirebaseInstanceId.getInstance().getToken();
        editor.putString("token",token);
        editor.commit();

        //회원가입 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkInput()){
                    Item_signup item_signup = new Item_signup(null,null, token, null, driver_name, null, "1");
                    String send = gson.toJson(item_signup);
                    trytologin(send);
                }



            }
        });
    }

    private boolean checkInput(){
        driver_name=name.getText().toString();

        if(!driver_name.equals("")){
            editor.putString("name",driver_name);
            editor.commit();

            return true;
        }else{
            return false;
        }
    }

    private void trytologin(final String send) {
        class checkEmail extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Response response;
            String get;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register_Activity.this, "로그인 중입니다.", null, true, true); //서버접속하기 전에 로딩 띄우기
                Log.i("Register_Activity", "백그라운드 시작");
            }

            @Override
            protected void onPostExecute(String s) { //서버접속했다면 로딩끄기
                super.onPostExecute(s);
                Log.i("register_response", s);
                loading.dismiss();
                item_response result = gson.fromJson(s, item_response.class);
                if (result.getResponse_code().equals("1")) {
                    Toast.makeText(Register_Activity.this, "환영합니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register_Activity.this,Main_Activity.class));
                    finish();
                } else {
                    Toast.makeText(Register_Activity.this, "인터넷 연결상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

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
                    response = client.newCall(request).execute();

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
