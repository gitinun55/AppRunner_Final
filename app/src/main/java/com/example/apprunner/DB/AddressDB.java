package com.example.apprunner.DB;

public class AddressDB {
    private String Address;
    private String District;
    private String MueangDistrict;
    private String province;
    private String Country_number;
    private String Name;
    private String Tel;
    private int id_user;
    private int id_add;
    private String transport;
    private String pacel_number;

    public AddressDB(int id_add, String Address,String District,String MueangDistrict,String province,String Country_number,String Name,String Tel,int id_user, String transport, String pacel_number){
        this.id_add = id_add;
        this.Address = Address;
        this.District = District;
        this.MueangDistrict = MueangDistrict;
        this.province = province;
        this.Country_number = Country_number;
        this.Name = Name;
        this.Tel = Tel;
        this.id_user = id_user;
        this.transport = transport;
        this.pacel_number = pacel_number;
    }
}
