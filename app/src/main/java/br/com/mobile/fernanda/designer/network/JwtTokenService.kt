package br.com.mobile.fernanda.designer.network

import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String);
data class LoginResponse(val token: String, val expirationTime: Long);

interface JwtTokenService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}