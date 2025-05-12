package br.com.mobile.fernanda.designer.network.manager

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String, expirationTime: Long) {
        prefs.edit().putString(HEADER_TOKEN, token).apply()
        prefs.edit().putString(HEADER_TOKEN_EXPIRY, expirationTime.toString()).apply()
    }

    fun getToken(): String? {
        return prefs.getString(HEADER_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit().remove(HEADER_TOKEN).apply()
    }

    companion object {
        private const val HEADER_TOKEN = "jwt_token"
        private const val HEADER_TOKEN_EXPIRY = "jwt_token_expiration"
    }
}