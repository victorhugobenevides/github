package com.itbenevides.github.data.di

import com.itbenevides.github.data.repository.GitHubRepository
import com.itbenevides.github.data.repository.GitHubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindGitHubRepository(repository: GitHubRepositoryImpl): GitHubRepository
}