package br.com.mobile.fernanda.designer.data.repository

import br.com.mobile.fernanda.designer.network.manager.TokenManager

interface AuthRepository {
    suspend fun renewToken(tokenManager: TokenManager): String
}