package com.example.public_apis_list_showcase.di

import com.example.public_apis_list_showcase.data.remote.api.ApiService
import com.example.public_apis_list_showcase.data.repositories.PublicApisSourceRepository
import com.example.public_apis_list_showcase.data.repositories.PublicApisSourceRepositoryImpl
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppTestModule {
    private const val BASE_URL = "http://127.0.0.1:8080"

    @Provides
    @Singleton
    fun provideNavigator() = Navigator()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesPublicApisSourceRepository(apiService: ApiService): PublicApisSourceRepository {
        return PublicApisSourceRepositoryImpl(apiService)
    }
}