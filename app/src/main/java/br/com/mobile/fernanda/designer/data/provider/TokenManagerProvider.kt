package br.com.mobile.fernanda.designer.data.provider

import android.content.Context
import br.com.mobile.fernanda.designer.data.repository.AuthRepository
import br.com.mobile.fernanda.designer.network.manager.TokenManager

object TokenManagerProvider {
    private var instance: TokenManager? = null

    fun init(context: Context, authRepository: AuthRepository) {
        if (instance == null) {
            instance = TokenManager(context.applicationContext, authRepository)
        }
    }

    fun getInstance(): TokenManager {
        return instance ?: throw IllegalStateException("TokenManager not initialized")
    }
}