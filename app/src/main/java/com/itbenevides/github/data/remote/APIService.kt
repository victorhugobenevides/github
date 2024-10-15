package com.itbenevides.github.data.remote

import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("search/repositories?q=language:Kotlin&amp;sort=stars&amp")
    fun getRepositories(@Query("page") page: Int = 0, @Query("per_page") itemsPerPage: Int = 20): Deferred<ResponseGitHub>

    @GET("repos/{user}/{repo}/pulls")
    fun getPullRequests(@Path("user") user: String?, @Path("repo") repo: String?): Deferred<List<PullRequest>>
}