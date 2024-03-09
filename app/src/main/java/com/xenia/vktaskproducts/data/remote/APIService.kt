package com.xenia.vktaskproducts.data.remote

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsDto

    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }

}