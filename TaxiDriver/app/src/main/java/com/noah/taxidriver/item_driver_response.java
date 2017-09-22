package com.noah.taxidriver;

import android.content.Context;

/**
 * Created by YH on 2017-09-19.
 */

public class item_driver_response {
    //고객 요청에 수락.
    public item_driver_response(Context context, String start_, String end_, String x, String y, String get_token) {
        this.context = context;
        this.start_ = start_;
        this.end_ = end_;
        this.x = x;
        this.y = y;
        this.get_token = get_token;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getStart_() {
        return start_;
    }

    public void setStart_(String start_) {
        this.start_ = start_;
    }

    public String getEnd_() {
        return end_;
    }

    public void setEnd_(String end_) {
        this.end_ = end_;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getGet_token() {
        return get_token;
    }

    public void setGet_token(String get_token) {
        this.get_token = get_token;
    }

    Context context;
    String start_;
    String end_;
    String x;
    String y;
    String get_token;
}
