package com.example.apprunner;

import com.example.apprunner.DB.AddressDB;
import com.example.apprunner.DB.CricketerDB;
import com.example.apprunner.DB.EventFormDB;
import com.example.apprunner.DB.ProfileUserDB;
import com.example.apprunner.DB.RegEventDB;
import com.example.apprunner.DB.UploadPaymentDB;
import com.example.apprunner.DB.UplodeStatisticsDB;
import com.example.apprunner.DB.history_cancelDB;
import com.example.apprunner.DB.userDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrganizerAPI {

    @POST("add_event")
    Call<ResultQuery> insertEvent(@Body EventFormDB body);

    @GET("login/{email}")
    Call<ResultQuery> login(@Path("email") String username);

    @GET("check_email/{email}")
    Call<ResultQuery> check_mail(@Path("email") String username);

    @POST("register")
    Call<ResultQuery> insertRegister(@Body userDB body);

    @GET("show_event")
    Call<List<UserListResponse>> getUserName(@Query("status") int type);

    @GET("search_event/{status}/{name_event}")
    Call<List<UserListResponse>> search_event(@Path("status") int status,@Path("name_event") String name_event);

    @GET("get_idEvent/{name_event}/{name_producer}")
    Call<ResultQuery> get_idEvent(@Path("name_event") String name_event,@Path("name_producer") String name_producer);

    @GET("check_address/{id_user}")
    Call<addressQuery> check_address(@Path("id_user") int id_user);

    @GET("value_distance/{id_add}")
    Call<List<Cricketer>> value_distance(@Path("id_add") int id_add);

    @GET("event_reg_list/{id_user}/{id_add}")
    Call<List<ResultQuery>> event_reg_list(@Path("id_user") int id_user,@Path("id_add") int id_add);

    @GET("event_list/{id_user}")
    Call<List<ResultQuery>> event_list(@Path("id_user") int id_user);

    @GET("event_organizer/{id_user}")
    Call<List<ResultQuery>> event_organizer(@Path("id_user") int id_user);

    @GET("event_payment_organizer/{id_user}")
    Call<List<ResultQuery>> event_payment_organizer(@Path("id_user") int id_user);

    @GET("list_name_reg_event/{id_add}")
    Call<List<ResultQuery>> list_name_reg_event(@Path("id_add") int id_add);

    @GET("user_detail_reg_event/{id_user}/{id_add}")
    Call<ResultQuery> user_detail_reg_event(@Path("id_user") int id_user,@Path("id_add") int id_add);

    @GET("show_user_event/{id_add}")
    Call<List<ResultQuery>> show_user_event(@Path("id_add")int id_add);

    @GET("profile_user/{id_user}")
    Call<ResultQuery> profile_user(@Path("id_user")int id_user);

    @POST("add_distance")
    Call<Cricketer> insertDistance(@Body CricketerDB body);

    @POST("add_address")
    Call<ResultQuery> insertAddress(@Body AddressDB body);

    @POST("reg_event")
    Call<ResultQuery> reg_event(@Body RegEventDB body);

    @POST("UploadStatic")
    Call<ResultQuery> UploadStatic(@Body UplodeStatisticsDB body);

    @PUT("edit_ProfileUser/{id_user}")
    Call<ResultQuery> edit_ProfileUser(@Path("id_user") int id_user,@Body ProfileUserDB body);

    @PUT("edit_AddressUser/{first_name}")
    Call<ResultQuery> edit_Address(@Path("first_name")String first_name);

    @POST("upload_payment")
    Call<ResultQuery> upload_payment(@Body UploadPaymentDB body);

    @GET("show_user_payment/{id_history_payment}")
    Call<ResultQuery> show_user_payment(@Path("id_history_payment")int id_history_payment);

    @GET("show_history_user_payment/{id_reg_event}")
    Call<ResultQuery> show_history_user_payment(@Path("id_reg_event")int id_reg_event);

    ///****////
    @GET("setName/{id_user}")
    Call<ResultQuery> setName(@Path("id_user")int id_user);

    @PUT("approve_event/{id_reg_event}")
    Call<ResultQuery> summit_payment(@Path("id_reg_event")int id_reg_event);

    @PUT("approve_history_payment/{id_history_payment}")
    Call<ResultQuery> approve_history_payment(@Path("id_history_payment")int id_history_payment);

    @PUT("cancel_event/{id_reg_event}")
    Call<ResultQuery> cancel_payment(@Path("id_reg_event")int id_reg_event);

    @PUT("cancel_history_payment/{id_history_payment}")
    Call<ResultQuery> cancel_history_payment(@Path("id_history_payment")int id_history_payment,@Body history_cancelDB body);

    //submit_type_distance
    @PUT("submit_type_distance/{id_add}/{id_user}")
    Call<ResultQuery> submit_type_distance(@Path("id_add")int id_add,@Path("id_user")int id_user);

    //submit_type_send
    @PUT("submit_type_send/{id_add}/{id_user}")
    Call<ResultQuery> submit_type_send(@Path("id_add")int id_add,@Path("id_user")int id_user);

    @GET("getId_reg_event/{id_user}/{id_add}")
    Call<ResultQuery> getId_reg_event(@Path("id_user")int id_user,@Path("id_add")int id_add);

    @GET("show_status_payment_user/{id_user}")
    Call<List<ResultQuery>> show_status_payment_user(@Path("id_user")int id_user);

    @GET("check_user_reg_event/{id_user}/{id_add}")
    Call<ResultQuery> check_user_reg_event(@Path("id_user")int id_user,@Path("id_add")int id_add);

    @GET("check_organizer_event/{id_add}")
    Call<ResultQuery> check_organizer_event(@Path("id_add")int id_add);

    @GET("list_payment_event/{id_user}")
    Call<List<ResultQuery>> list_payment_event(@Path("id_user")int id_user);

    @GET("check_status_payment/{id_reg_event}")
    Call<ResultQuery> check_status_payment(@Path("id_reg_event")int id_reg_event);

    @GET("list_user_reg_payment/{id_add}")
    Call<List<ResultQuery>> list_user_reg_payment(@Path("id_add")int id_add);

    @GET("check_send_submit/{id_add}")
    Call<List<ResultQuery>> check_send_submit(@Path("id_add")int id_add);

    @GET("check_status_event/{id_add}")
    Call<ResultQuery> check_status_event(@Path("id_add")int id_add);

    @GET("check_user_payment_event/{id_user}")
    Call<List<ResultQuery>> check_user_payment_event(@Path("id_user")int id_user);

    @GET("show_stat_distance/{id_user}/{id_add}")
    Call<List<ResultQuery>> show_stat_distance(@Path("id_user")int id_user,@Path("id_add")int id_add);

    @GET("list_history_distance/{id_user}/{id_add}")
    Call<List<UserListResponse>> list_history_distance(@Path("id_user")int id_user,@Path("id_add")int id_add);

    @GET("show_address/{id_user}/{id_add}")
    Call<addressQuery> show_address(@Path("id_user")int id_user,@Path("id_add")int id_add);

    //show_payment_event
    @GET("show_payment/{id_add}")
    Call<ResultQuery> show_payment(@Path("id_add")int id_add);

    @GET("show_user_send_reward/{id_add}")
    Call<List<ResultQuery>> show_user_send_reward(@Path("id_add")int id_add);

    @PUT("update_transport/{id_user}/{id_add}")
    Call<ResultQuery> update_transport(@Path("id_user")int id_user,@Path("id_add")int id_add,@Body AddressDB body);

}

