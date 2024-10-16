package com.itbenevides.core.data.model

import com.itbenevides.core.data.model.Repository

data class ResponseGitHub(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
