package com.noah.taxidriver;

/**
 * Created by YH on 2017-09-01.
 */

public class item_user {
    String name;
    String token;
    String mode;
    String x;
    String y;


    public item_user(String name, String token, String mode, String x, String y) {
        this.name = name;
        this.token = token;
        this.mode = mode;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWho() {
        return mode;
    }

    public void setWho(String mode) {
        this.mode = mode;
    }
}
