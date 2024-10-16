package com.itbenevides.github.ui.feature.github.repository

import com.itbenevides.core.data.model.Repository
import com.itbenevides.github.ui.feature.github.StatusResult

data class RepositoryInfoState(
    val status: StatusResult,
    val data: List<Repository> = mutableListOf(),
    val errorMessages: String = ""
)

