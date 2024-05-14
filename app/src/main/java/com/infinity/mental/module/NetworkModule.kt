package com.infinity.mental.module

import com.google.gson.GsonBuilder
import com.infinity.mental.data.network.MainApi
import com.infinity.mental.utils.AuthInterceptor
import com.infinity.mental.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Builder {
        val gson = GsonBuilder().setLenient().create()

        return Builder()
            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

//    @Singleton
//    @Provides
//    fun providesUserAPI(retrofitBuilder: Builder, okHttpClient: OkHttpClient): AuthApi {
//        return retrofitBuilder.client(okHttpClient).build()
//            .create(AuthApi::class.java)
//    }

    @Singleton
    @Provides
    fun BlogApi(
        retrofitBuilder: Builder,
        okHttpClient: OkHttpClient
    ): MainApi {
        return retrofitBuilder.client(okHttpClient).build()
            .create(MainApi::class.java)
    }


}