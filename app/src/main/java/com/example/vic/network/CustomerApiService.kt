package com.example.vic.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// import retrofit2.http.*

private const val BASE_URL = NetworkConfig.BASE_URL

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .authenticator(JwtAuthenticator())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface CustomerApiService {

    @GET("customers")
    fun getCustomerIndexes(): Deferred<ApiCustomerIndexContainer>

    @GET("customers/{id}")
    fun getCustomerById(@Path("id") id: Long?): Deferred<ApiCustomerContainer>

    @GET("customers/alldetails")
    fun getAllCustomers(): Deferred<ApiCustomersContainer>

    @POST("customers")
    fun createCustomer(@Body customer: ApiCustomerContainer): Deferred<ApiCustomerPostResponse>
}

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}
