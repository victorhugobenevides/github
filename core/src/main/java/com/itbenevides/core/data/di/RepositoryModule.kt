package com.itbenevides.core.data.di

import com.itbenevides.core.domain.repository.GitHubRepository
import com.itbenevides.core.domain.repository.GitHubRepositoryImpl
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