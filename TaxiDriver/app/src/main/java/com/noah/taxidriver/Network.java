package com.noah.taxidriver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by YH on 2017-09-19.
 */
//통신 클래스
public class Network {


    //서버로 fcm 메시지를 보내라고 요청
  public  static void push(final String send, final Context context,final Handler handler) {
        class checkEmail extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Response response;
            String get;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Please Wait", null, true, true); //서버접속하기 전에 로딩 띄우기
                Log.i("Register_Activity","백그라운드 시작");
            }

            @Override
            protected void onPostExecute(String s) { //서버접속했다면 로딩끄기
                super.onPostExecute(s);
                Log.i("Register_Activity","백그라운드 끝");
                loading.dismiss();
                Message m = new Message();
                if (s.equals("fail")){//실패시

                    Toast.makeText(context, "서버와의 통신이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();

                }else{//성공시
                    m.arg1= 1;
                }
                handler.sendMessage(m);

            }

            @Override
            protected String doInBackground(String... params) {
                //이부분에서 data를 key로  메시지를 보내준다.
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("send", send)
                        .build();

                //request
                Request request = new Request.Builder()
                        .url("http://teampinky.vps.phps.kr/API/call_driver.php")
                        .post(body)
                        .build();

                try {
                    response =   client.newCall(request).execute();

                    get = response.body().string();
                    Log.i("Frag_first",get);
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
