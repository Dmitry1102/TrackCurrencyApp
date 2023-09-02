package com.example.trackcurrencyapp.di

import com.example.trackcurrencyapp.BuildConfig
import com.example.trackcurrencyapp.data.service.IPopularService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

   @Singleton
   @Provides
   fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
      return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
   }

   @Singleton
   @Provides
   fun provideLoggingInterceptor(): HttpLoggingInterceptor {
      return HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
   }

   @Provides
   @Singleton
   fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
      return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .client(okHttpClient)
         .build()
   }

   @Provides
   @Singleton
   fun providePopularService(retrofit: Retrofit): IPopularService {
      return retrofit.create(
         IPopularService::class.java)
   }
}