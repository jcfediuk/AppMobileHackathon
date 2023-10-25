package com.example.appmobilehackathon.ApiService

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.appmobilehackathon.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnClickListener { navigateToSearchView() }


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/simple/supported_vs_currencies")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getSupportedCurrencies()

        call.enqueue(object : Callback<RespuestaAPI> {
            override fun onResponse(call: Call<RespuestaAPI>, response: Response<RespuestaAPI>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    if (respuesta != null) {
                        val coins = respuesta.coins

                        for (coins in coins) {
                            Log.d("Moneda", coins)
                        }
                    } else {
                        Log.e("API Call", "Respuesta nula")
                    }
                } else {
                    Log.e("API Call", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RespuestaAPI>, t: Throwable) {
                Log.e("API Call", "Error: ${t.message}")
            }

        })
    }

    private fun navigateToSearchView() {
        val intent = Intent(this, MainActivity::class.java )
        startActivity(intent)
    }
}

data class RespuestaAPI(
    val coins: List<String>
)
