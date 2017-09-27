package com.noah.taxidriver;

/**

 * Created by YH on 2017-09-12.
 */



public class item_response {
    String response_code;
    String data;

    public item_response(String response_code, String data) {
        this.response_code = response_code;
        this.data = data;

    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
