package com.example.meetapp.di

import com.example.meetapp.common.Constants
import com.example.meetapp.data.remote.MeetApi
import com.example.meetapp.data.repository.MeetRepositoryImpl
import com.example.meetapp.domain.repository.MeetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMeetApi(): MeetApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeetApi::class.java)
    }

    @Provides
    fun provideMeetRepository(api: MeetApi): MeetRepository {
        return MeetRepositoryImpl(api)
    }
}