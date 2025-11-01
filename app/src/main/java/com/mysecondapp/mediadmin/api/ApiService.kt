package com.mysecondapp.mediadmin.api

import com.mysecondapp.mediadmin.model.UserDataModel
import com.mysecondapp.mediadmin.model.UserDataModelItem
import com.mysecondapp.mediadmin.model.UserOperationModel
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {


    @GET("listalldata")
    suspend fun GetAllUsersData() : Response<UserDataModel>

    @FormUrlEncoded
    @POST("readspecificuser")
    suspend fun GetSpecificUser(
        @Field("user_id") UserId : String
    ): Response<UserDataModelItem>

    @FormUrlEncoded
    @HTTP(path = "deleteuserspecific", hasBody = true, method = "DELETE")
    suspend fun deleteSpecificUser(
        @Field("user_id") UserId: String
    ): Response<UserDataModel>

    @FormUrlEncoded
    @PATCH("updateuser")
    suspend fun UpdateUser(
        @Field("name") Name : String?,
        @Field("password") password : String?,
        @Field("phone_number") phNumber : String?,
        @Field("email") Email : String?,
        @Field("address") address: String?,
        @Field("pincode") pincode : String?,
        @Field("block_status") BlockStatus : String?
    ) : Response<UserOperationModel>

    @FormUrlEncoded
    @PATCH("approveuser")
    suspend fun ApproveUser(
        @Field("user_id") UserId: String,
        @Field("is_approved") ApproveStatus : String
    ): Response<UserOperationModel>

    @FormUrlEncoded
    @PATCH("updateuser")
    suspend fun updateUser(
        @Field("user_id") UserId: String?,
        @Field("block_status") BlockStatus: String?
    ): Response<UserOperationModel>

}