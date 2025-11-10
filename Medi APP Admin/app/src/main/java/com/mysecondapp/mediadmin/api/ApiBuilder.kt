package com.mysecondapp.mediadmin.api

import com.mysecondapp.mediadmin.model.UserDataModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiBuilder {
    val Api : ApiService = Retrofit.Builder().
        client(OkHttpClient.Builder().build()).
        baseUrl("https://blacklight121.pythonanywhere.com/").
        addConverterFactory(GsonConverterFactory.create()).
        build().
        create(ApiService :: class.java)

}