package com.itbenevides.core.data.model

data class Head(
    val label: String,
    val ref: String,
    val sha: String,
    val user: User,
    val repo: Repository
)
