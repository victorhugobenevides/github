package com.itbenevides.github.ui.feature.github.pullrequest

import com.itbenevides.core.data.model.PullRequest
import com.itbenevides.github.ui.feature.github.StatusResult

data class PullRequestInfoState(
    val status: StatusResult,
    val data: List<PullRequest> = mutableListOf(),
    val errorMessages: String = ""
)

