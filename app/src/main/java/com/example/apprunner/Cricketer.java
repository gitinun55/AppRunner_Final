package com.example.apprunner;

import java.io.Serializable;

public class Cricketer implements Serializable {

    private int Add_id;
    private int Price;
    private String NameDistance;
    private int Distance;
    private String name_event;
    private int distance;
    private int num_register;
    private int price;
    private int Num_register;

    public Cricketer(int add_id, String namedistance, int distance, int price, int num_register) {
        Add_id = add_id;
        NameDistance = namedistance;
        Price = price;
        Distance = distance;
        num_register = num_register;
    }
    public int getId_add(){
        return Add_id;
    }

    public String getNameDistance(){
        return NameDistance;
    }

    public void setNameDistance(String namedistance){
        NameDistance = namedistance;
    }

    public int getPrice() {
        return Price;
    }

    public int getprice(){return price;}

    public void setPrice(int price) { Price = price;}

    public int getdistance() {
        return distance;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

    public int getNum_register() {
        return Num_register;
    }

    public void setNum_registere(int num_register) {
        Num_register = num_register;
    }

    public int getnum_register() {return num_register;}

}
