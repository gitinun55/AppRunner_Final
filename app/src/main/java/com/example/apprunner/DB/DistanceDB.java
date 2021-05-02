package com.example.apprunner.DB;

public class DistanceDB {
//    String id_profile_distance;
    String Date;
    String time;
    String distance;
    String calorie;

    public DistanceDB ( String Date, String time, String distance, String calorie) {
//        this.id_profile_distance = id_profile_distance;
        this.Date = Date;
        this.time = time;
        this.distance = distance;
        this.calorie = calorie;
    }

//    public String getId_profile_distance(){
//        return id_profile_distance;
//    }

    public String getDate(){
        return Date;
    }

    public String getTime(){
        return time;
    }

    public String getDistance(){
        return distance;
    }

    public String getCalorie(){
        return calorie;
    }
}
