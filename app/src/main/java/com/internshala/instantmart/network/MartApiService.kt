package com.internshala.instantmart.network

import com.internshala.instantmart.data.InternetItem
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
 import retrofit2.http.GET

import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL="https://training-uploads.internshala.com"
private val retrofit=Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory(
            "application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()


interface MartApiService{
    @GET("android/grocery_delivery_app/items.json")
    suspend fun getItems():List<InternetItem>

}

object MartApi{
    val retrofitService: MartApiService by lazy {
        retrofit.create(MartApiService::class.java )
    }
}