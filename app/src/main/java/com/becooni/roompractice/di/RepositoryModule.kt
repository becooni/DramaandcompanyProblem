package com.becooni.roompractice.di

import com.becooni.roompractice.network.GithubClient
import com.becooni.roompractice.persistence.UserDao
import com.becooni.roompractice.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        githubClient: GithubClient,
        userDao: UserDao
    ) = GithubRepository(githubClient, userDao)
}