package br.com.mobile.fernanda.designer.data.repository.impl

import android.util.Log
import br.com.mobile.fernanda.designer.data.repository.AuthRepository
import br.com.mobile.fernanda.designer.network.ApiClient
import br.com.mobile.fernanda.designer.network.LoginRequest
import br.com.mobile.fernanda.designer.network.manager.TokenManager

class AuthRepositoryImpl: AuthRepository {

    override suspend fun renewToken(tokenManager: TokenManager): String {
        val jwtTokenService = ApiClient.generateJwtToken(tokenManager)
        val payload = LoginRequest("admin", "admin")
        val jwtResponse = jwtTokenService.login(payload)
        Log.d("JWT LOGIN", "doLogin: ${jwtResponse.token}")
        return jwtResponse.token
    }
}