package com.noah.taxidriver;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YH on 2017-09-16.
 */

public class Call_driver_dialog extends Dialog {
    public Call_driver_dialog(@NonNull Context context,String start,String end,String x,String y,String get_token) {
        super(context);
        this.start_ = start;
        this.end_ = end;
        this.x = x;
        this.y = y;
        this.get_token = get_token;
    }
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
            }
        });

    no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    });
    }
}
