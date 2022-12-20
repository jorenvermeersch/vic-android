package com.example.vic.network

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = NetworkConfig.BASE_URL // + "customers/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface CustomerApiService {
    @GET("f96362ed-9b89-4991-bf5a-2c4b8c86dc3b/")
    fun getCustomerIndexes(): Deferred<ApiCustomerIndexContainer>

//    f7022cd9-a9be-4f03-83fd-9ae4b4775312/
    @GET("{id}/")
//    @GET("33413381-2f5e-4bef-93de-4886cd79a3e4/")
//    @GET("50228511-198d-48b1-b3a2-07207ec53bce/")
//    @GET("b67f617f-4961-42eb-b6ce-61aaf31e5e4b/")
    fun getCustomerById(@Path("id") id: String): Deferred<ApiCustomerContainer>
}

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}
