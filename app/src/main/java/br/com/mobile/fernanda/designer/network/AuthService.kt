package br.com.mobile.fernanda.designer.network

import retrofit2.http.Body
import retrofit2.http.POST

data class AuthenticateRequest(val email: String, val password: String)
data class AuthenticateResponse(val id: Int, val name: String, val phone: String, val email: String)

interface AuthService {
    @POST("v1/customer/authenticate")
    suspend fun authenticate(
        @Body request: AuthenticateRequest
    ): AuthenticateResponse
}