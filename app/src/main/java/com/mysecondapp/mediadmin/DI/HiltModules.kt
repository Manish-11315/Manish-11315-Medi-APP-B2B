package com.mysecondapp.mediadmin.DI

import com.mysecondapp.mediadmin.api.ApiBuilder
import com.mysecondapp.mediadmin.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideretrofitobj() : ApiBuilder{
        return ApiBuilder
    }

    @Provides
    @Singleton
    fun providesRepo(
        ApiInstance : ApiBuilder
    ): Repo{
        return Repo(ApiInstance)
    }
}