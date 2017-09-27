package com.noah.taxidriver;

/**
 * Created by YH on 2017-09-18.
 */

public class Item_response_driver {
    String token;
    String x;
    String y;
    String destination; //선택한 게시물 넘버
    String start_address; //탑승자의 현위치
    String flag;
    String name;
    String car_num; // 차량넘버
    public Item_response_driver(String car_num, String name, String token, String x, String y, String destination, String start_address, String flag) {
        this.car_num = car_num;
        this.name = name;
        this.token = token;
        this.x = x;
        this.y = y;
        this.destination = destination;
        this.start_address = start_address;
        this.flag = flag;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
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
