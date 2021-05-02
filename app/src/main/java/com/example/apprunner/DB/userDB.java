package com.example.apprunner.DB;

public class userDB {

    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String gender;
    private String bd_date;
    private String type;

    public userDB(String first_name, String last_name, String email, String password, String gender, String bd_date, String type){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.bd_date = bd_date;
        this.type = type;
    }

}
