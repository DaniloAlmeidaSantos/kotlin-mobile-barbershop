package br.com.mobile.fernanda.designer.domain.utils

import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.util.Date

object JwtUtils {

    fun isTokenExpired(token: String): Boolean {
        try {
            val parts = token.split(".")
            if (parts.size != 3) return true

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            val expiration = json.getLong("exp")
            val expiryDate = Date(expiration * 1000)

            val now = Date()
            return expiryDate.before(Date(now.time + 60_000))
        } catch (e: Exception) {
            Log.e("Error to decode expiry time", e.message ?: "")
            return true
        }
    }

}