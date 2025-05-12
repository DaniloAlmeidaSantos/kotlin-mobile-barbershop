package br.com.mobile.fernanda.designer.network

import br.com.mobile.fernanda.designer.network.interceptor.AuthInterceptor
import br.com.mobile.fernanda.designer.network.manager.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/manager-barbershop/";

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun generateJwtToken(tokenManager: TokenManager): JwtTokenService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(tokenManager))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JwtTokenService::class.java)

    }

    fun authenticate(tokenManager: TokenManager): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(tokenManager))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

//    val authService: AuthService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(AuthService::class.java)
//    }
//
//    val generateJwtToken: JwtTokenService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(JwtTokenService::class.java)
//    }


}