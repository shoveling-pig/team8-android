package com.android.example.podomarket.di

import android.app.Application
import android.content.SharedPreferences
import com.android.example.podomarket.BuildConfig
import com.android.example.podomarket.di.NetworkConst.BASE_URL
import com.android.example.podomarket.di.NetworkConst.PREFS_FILENAME
import com.android.example.podomarket.di.NetworkConst.TOKEN_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConst {
    const val BASE_URL = "https://api.podomarket.shop/"
    const val PREFS_FILENAME = "prefs"
    const val TOKEN_KEY = "token"
}

val networkModule = module {
    single { provideSharedPreference(androidApplication()) }
    single<SharedPreferences.Editor> { provideSharedPrefereceEditor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), BASE_URL) }
}

private fun provideSharedPreference(androidApplication: Application): SharedPreferences =
    androidApplication.getSharedPreferences(
        PREFS_FILENAME,
        android.content.Context.MODE_PRIVATE
    )

private fun provideSharedPrefereceEditor(sharedPreferences: SharedPreferences) =
    sharedPreferences.edit()

private fun provideOkHttpClient(prefs: SharedPreferences): OkHttpClient {
    var okHttpClient = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = okHttpClient
            .addInterceptor(loggingInterceptor)
    }

    val authInterceptor = object : Interceptor {
        private val AUTHORIZATION = "Authorization"
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = prefs.getString(TOKEN_KEY, null)
            return if (token != null) {
                val auth = "Token $token"
                val request = chain.request()
                val builder = request.newBuilder().header(AUTHORIZATION, auth)
                chain.proceed(builder.build())
            } else {
                chain.proceed(chain.request())
            }
        }
    }

    return okHttpClient
        .addInterceptor(authInterceptor)
        .build()
}


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

