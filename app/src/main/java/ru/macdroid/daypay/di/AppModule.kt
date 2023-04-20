package ru.macdroid.daypay.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.macdroid.daypay.data.remote.api.AppRetrofitClient
import ru.macdroid.daypay.domain.repository.AppRemoteRepository

import ru.macdroid.daypay.data.remote.repository.AppRemoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://holidays-calendar-ru.vercel.app/"
    @Provides
    @Singleton
    fun provideAppClient(): AppRetrofitClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(AppRetrofitClient::class.java)
    }

    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build();

    private  var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideMainRemoteRepository(api: AppRetrofitClient): AppRemoteRepository {
        return AppRemoteRepositoryImpl(api)
    }

}