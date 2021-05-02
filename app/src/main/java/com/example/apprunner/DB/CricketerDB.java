package com.example.apprunner.DB;

public class CricketerDB {
    private int id_add;
    private String name_event;
    private String name_distance;
    private int price;
    private int distance;
    private int num_register;

    public CricketerDB(int id_add,String name_event,String name_distance,int distance ,int price, int num_register){
        this.id_add = id_add;
        this.name_event = name_event;
        this.name_distance = name_distance;
        this.distance = distance;
        this.price = price;
        this.num_register = num_register;
    }
}
