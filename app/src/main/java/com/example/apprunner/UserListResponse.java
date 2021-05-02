package com.example.apprunner;

public class UserListResponse {

    private String name_event;
    private String name_producer;
    private String pic_event;
    String date_reg_start,date_reg_end,detail;
    int id_add;
    private int num_register;
    private float distance;

    public int get_eventID(){
        return id_add;
    }
    public  String getName_event(){
        return name_event;
    }
    public void setName_event(){
        this.name_event = name_event;
    }
    public  String getName_producer(){
        return name_producer;
    }
    public  String getPic_event(){
        return pic_event;
    }
    public  void setName_producer(){
        this.name_producer = name_producer;
    }
    public String date_reg_start(){
        return date_reg_start;
    }
    public String date_reg_end(){
        return date_reg_end;
    }
    public String getDetail(){ return detail; }
    public int getNum_register(){return num_register;}
    public float getDistance(){return distance;}
}
