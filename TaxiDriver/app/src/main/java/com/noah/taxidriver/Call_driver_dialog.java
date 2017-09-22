package com.noah.taxidriver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.Activity.Act_loding_matching;

/**
 * Created by YH on 2017-09-16.
 */

public class Call_driver_dialog extends Dialog {
    public Call_driver_dialog(@NonNull Context context,String start,String end,String x,String y,String get_token) {
        super(context);
        this.context = context;
        this.start_ = start;
        this.end_ = end;
        this.x = x;
        this.y = y;
        this.get_token = get_token;

    }
    Context context;
    String start_;
    String end_;
    String x;
    String y;
    String get_token;
TextView start;
    TextView end;
    Button ok;//5
    Button no;//6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_request);
    end = (TextView)findViewById(R.id.textView2);
    start = (TextView)findViewById(R.id.textView);
        ok = (Button)findViewById(R.id.button5);
        no = (Button)findViewById(R.id.button6);
start.setText(start_);
        end.setText(end_);

        start.setText(start_);
        end.setText(end_);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //매칭 됬다고 보내준다.

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 토큰값을 사용하여 매칭됨을 드라이버에게 전송함.
               // get_token;
                //고객의 응답을 기다리는 화면으로 넘어간다.
                Gson gson = new Gson();
              String send = gson.toJson(new item_matching(get_token,x,y,end_,start_,"driver_ok"));
                send_message_handler handler = new send_message_handler();
                Network.push(send,getContext(),handler);

            }
        });

    no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    });
    }

    class send_message_handler extends Handler{//매칭의 모든 것을 Network하나로 하기 때문에,
        //응답후 결과는 모두 핸들 메시지를 사용해야함.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0 :

                    dismiss();
                case 1 :
                    context.startActivity(new Intent(context, Act_loding_matching.class));
            }
        }
    }
}
