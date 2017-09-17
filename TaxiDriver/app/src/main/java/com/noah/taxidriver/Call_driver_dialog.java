package com.noah.taxidriver;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YH on 2017-09-16.
 */

public class Call_driver extends Dialog {
    public Call_driver(@NonNull Context context) {
        super(context);
    }
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
    }
}
