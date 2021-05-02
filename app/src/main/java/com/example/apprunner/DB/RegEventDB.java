package com.example.apprunner.DB;

public class RegEventDB {
    private  int id_user;
    private String first_name;
    private String last_name;
    private String tel;
    private String id_card;
    private String nationality;
    private String blood;
    private String distance;
    private String emergency;
    private String relation;
    private String relationTel;
    private int id_add;
    private String name_event;
    private String name_producer;
    private String pic_event;

    public RegEventDB(int id_user,String first_name,String last_name,String tel,String id_card,String nationality,String blood,String distance,String emergency,String relation,String relationTel,int id_add,String name_event,String name_producer,String pic_event){
        this.id_user = id_user;
        this.first_name = first_name;
        this.last_name = last_name;
        this.tel = tel;
        this.id_card = id_card;
        this.nationality = nationality;
        this.blood = blood;
        this.distance = distance;
        this.emergency = emergency;
        this.relation = relation;
        this.relationTel = relationTel;
        this.id_add = id_add;
        this.name_event = name_event;
        this.name_producer = name_producer;
        this.pic_event = pic_event;
    }
}
