package com.example.vic.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = NetworkConfig.BASE_URL // + "virtual-machines/"

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

interface VirtualMachineApiService {
    @GET("virtualmachine/{id}/")
    fun getVirtualMachineById(@Path("id") id: Long?): Deferred<ApiVirtualMachineContainer>

    @GET("virtualmachines")
    fun getAllVirtualMachines(): Deferred<ApiVirtualMachinesContainer>
}

object VirtualMachineApi {
    val retrofitService: VirtualMachineApiService by lazy {
        retrofit.create(VirtualMachineApiService::class.java)
    }
}
