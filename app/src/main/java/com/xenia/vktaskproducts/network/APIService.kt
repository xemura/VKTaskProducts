package com.xenia.vktaskproducts.network

import com.xenia.vktaskproducts.model.Products
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {

    @GET("/products")
    fun getProducts(): Call<Products>

}

private const val BASE_URL = "https://dummyjson.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object ProductApi {
    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}