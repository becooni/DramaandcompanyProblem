package com.becooni.dramaandcompanyproblem.di

import com.becooni.dramaandcompanyproblem.network.GithubClient
import com.becooni.dramaandcompanyproblem.persistence.UserDao
import com.becooni.dramaandcompanyproblem.repository.ConsonantUtil
import com.becooni.dramaandcompanyproblem.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideConsonantUtil() = ConsonantUtil()

    @Provides
    @Singleton
    fun provideRepository(
        githubClient: GithubClient,
        userDao: UserDao,
        consonantUtil: ConsonantUtil
    ) = GithubRepository(githubClient, userDao, consonantUtil)
}