package com.nativeboys.password.manager.di

import com.nativeboys.password.manager.data.services.UserService
import com.nativeboys.password.manager.data.services.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindUserService(
        userServiceImpl: UserServiceImpl
    ): UserService

}