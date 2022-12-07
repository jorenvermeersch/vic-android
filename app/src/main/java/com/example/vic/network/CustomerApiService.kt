package com.example.vic.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
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
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(ScalarsConverterFactory.create())
//    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface CustomerApiService {

//    @GET("/realestate")
    @GET(".")
    fun getCustomerIndexes(): Call<String>

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
