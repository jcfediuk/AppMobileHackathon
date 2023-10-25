package com.example.appmobilehackathon.ApiService


import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @GET("/api.coingecko.com/api/v3/simple/supported_vs_currencies")
    fun getSupportedCurrencies(): Call<RespuestaAPI>

}



