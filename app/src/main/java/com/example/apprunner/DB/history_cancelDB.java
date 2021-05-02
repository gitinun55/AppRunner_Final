package com.example.apprunner.DB;

public class history_cancelDB {
    private int id_user;
    private int id_add;
    private String name_event;
    private String first_name;
    private String last_name;
    private String image_link;
    private String date;
    private String time;
    private String bank;
    private int type_submit;
    private String detail_cancel;

    public history_cancelDB(int id_user,int id_add,String name_event,String first_name,String last_name,String image_link
    ,String date,String time,String bank,int type_submit,String detail_cancel){
        this.id_user = id_user;
        this.id_add = id_add;
        this.name_event = name_event;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image_link = image_link;
        this.date = date;
        this.time = time;
        this.bank = bank;
        this.type_submit = type_submit;
        this.detail_cancel = detail_cancel;
    }
}
