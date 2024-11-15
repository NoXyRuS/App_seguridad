package com.example.apisecurityapp.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(LoggingInterceptor())
        .build()

    // Crea un interceptor para loggear
    private fun LoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            // Log del request
            Log.d("API", "Request: ${request.url}")

            // Proceso la solicitud y obtengo la respuesta
            val response = chain.proceed(request)

            // Leemos el cuerpo de la respuesta y lo logueamos
            val responseBody = response.body
            val responseBodyString = responseBody?.string()

            // Logueo la respuesta
            Log.d("API", "Response: $responseBodyString")

            // Volver a crear la respuesta con el cuerpo leído, para que Retrofit lo procese correctamente
            return@Interceptor response.newBuilder()
                .body(okhttp3.ResponseBody.create(responseBody?.contentType(), responseBodyString ?: ""))
                .build()
        }
    }


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
