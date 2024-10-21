package com.itbenevides.core.domain.usecase

import com.itbenevides.core.data.model.PullRequest

interface GetPullRequestUseCase  {
    suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest>
}