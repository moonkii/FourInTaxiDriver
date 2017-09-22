package com.noah.taxidriver;

/**
 * Created by YH on 2017-09-18.
 */

public class item_matching {
    String token;
    String x;
    String y;
    String destination; //선택한 게시물 넘버
    String start_address; //탑승자의 현위치
    String flag;

    public item_matching(String token, String x, String y, String destination, String start_address,String flag) {
        this.token = token;
        this.x = x;
        this.y = y;
        this.destination = destination;
        this.start_address = start_address;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }


}
