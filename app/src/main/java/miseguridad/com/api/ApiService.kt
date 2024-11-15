package com.example.apisecurityapp.api

import com.example.apisecurityapp.model.LoginRequest
import com.example.apisecurityapp.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")  // Asegúrate de que la ruta sea correcta según tu servidor
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
