package com.example.vic.network

import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.CustomerIndexData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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

//    @GET("/realestate")
//    @GET(".")
//    fun getCustomerIndexes(): Call<String>

    @GET(".")
    fun getCustomerIndexes(): Deferred<CustomerIndexData>

//    @GET("{id}")
//    fun getCustomerById(@Path("id") id: Long): Deferred<Customer>
//
//    @GET("indexes")
//    fun getCustomerIndexes(): Deferred<List<CustomerIndex>>
//
//    @POST
//    fun insertCustomer(@Body customer: Customer)
}

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}
