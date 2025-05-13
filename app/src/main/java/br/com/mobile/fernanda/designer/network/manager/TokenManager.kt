package br.com.mobile.fernanda.designer.network.manager

import android.content.Context
import br.com.mobile.fernanda.designer.data.repository.AuthRepository
import br.com.mobile.fernanda.designer.domain.utils.JwtUtils

class TokenManager(context: Context, private val authRepository: AuthRepository) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private fun saveToken(token: String) {
        prefs.edit().putString(HEADER_TOKEN, token).apply()
    }

    private fun getToken(): String? {
        return prefs.getString(HEADER_TOKEN, null)
    }

    suspend fun getValidToken(): String {
        val token = getToken()

        return if (token != null && !JwtUtils.isTokenExpired(token)) {
            token
        } else {
            val newToken = authRepository.renewToken(this)
            saveToken(newToken)
            newToken
        }
    }

    fun clearToken() {
        prefs.edit().remove(HEADER_TOKEN).apply()
    }

    companion object {
        private const val HEADER_TOKEN = "jwt_token"
    }
}