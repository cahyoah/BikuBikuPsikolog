package com.example.adhit.bikubikupsikolog.data.network;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by adhit on 05/01/2018.
 */

public interface Api {

    //qiscus

    @POST("/api/v2/rest/create_room")
    @FormUrlEncoded
    Call<JsonObject> createChatRoomSDK(@Field("name") String roomName,
                                       @Field("creator") String creator,
                                       @Field("participants[]") ArrayList<String> participants);

    @POST("/api/v2/rest/get_rooms_info")
    @FormUrlEncoded
    Call<JsonObject> getRoomInfo(@Field("user_email") String userEmail,
                                 @Field("room_id[]") ArrayList<String> roomId);


    @POST("/api/v2/rest/post_comment")
    @FormUrlEncoded
    Call<JsonObject> sendMessage(@Field("sender_email") String senderEmail,
                                 @Field("room_id") String roomId,
                                 @Field("message") String message);


    @GET("/api/v2/rest/get_user_rooms")
    Call<JsonObject> getChatRoomHistory(@Query("user_email") String userEmail,
                                        @Query("show_participants") boolean showParticipants);

    //bikubiku
    @POST("user/login")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("username") String username,
                           @Field("password") String password);

    @GET("ruangbelajar/transaksikabim?layanan=psikologi")
    Call<JsonObject> getAllTransaction();

    @POST("ruangbelajar/updatestatus")
    @FormUrlEncoded
    Call<JsonObject> changeTransactionStatus(@Field("layanan") String service,
                                             @Field("invoice") String invoice,
                                             @Field("status") String status);
}
