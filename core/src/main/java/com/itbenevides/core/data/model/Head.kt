package com.itbenevides.core.data.model

import com.itbenevides.core.data.model.Repository

data class Head(
    val label: String,
    val ref: String,
    val sha: String,
    val user: User,
    val repo: Repository
)
