package com.itbenevides.core.data.model

data class ResponseGitHub(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
