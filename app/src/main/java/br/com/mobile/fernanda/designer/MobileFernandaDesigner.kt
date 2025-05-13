package br.com.mobile.fernanda.designer

import android.app.Application
import br.com.mobile.fernanda.designer.data.provider.TokenManagerProvider
import br.com.mobile.fernanda.designer.data.repository.impl.AuthRepositoryImpl

class MobileFernandaDesigner : Application() {
    override fun onCreate() {
        super.onCreate()

        val authRepository = AuthRepositoryImpl()
        TokenManagerProvider.init(this, authRepository)
    }
}