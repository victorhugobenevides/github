package com.itbenevides.github.data.model

data class Team(
    val id: Int,
    val node_id: String,
    val url: String,
    val html_url: String,
    val name: String,
    val slug: String,
    val description: String,
    val privacy: String,
    val permission: String,
    val notification_setting: String,
    val members_url: String,
    val repositories_url: String,
    val parent: Any?
)
