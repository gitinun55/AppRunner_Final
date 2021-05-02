package com.example.apprunner.DB;

public class UplodeStatisticsDB {

    private String date;
    private Float distance;
    private String cal;
    private String pace;
    private int id_user;
    private int id_add;

    public UplodeStatisticsDB(int id_user,int id_add,String date,Float distance,String cal,String pace){
        this.id_user = id_user;
        this.id_add = id_add;
        this.date = date;
        this.distance = distance;
        this.cal = cal;
        this.pace = pace;
    }

}
