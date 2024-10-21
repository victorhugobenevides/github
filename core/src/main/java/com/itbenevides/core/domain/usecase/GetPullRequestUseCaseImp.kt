package com.itbenevides.core.domain.usecase

import com.itbenevides.core.data.model.PullRequest
import com.itbenevides.core.data.repository.GitHubRepository
import javax.inject.Inject

class GetPullRequestUseCaseImp @Inject constructor(private val gitHubRepository: GitHubRepository): GetPullRequestUseCase {
    override suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest> {
        return gitHubRepository.getPullRequests(user = user, repo = repo)
    }
}