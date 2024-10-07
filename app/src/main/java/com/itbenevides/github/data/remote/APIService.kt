package com.itbenevides.github.data.remote

import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


interface APIService {
    @GET("search/repositories?q=language:Kotlin&amp;sort=stars&amp")
    fun getRepositories(@Query("page") page: Int = 0, @Query("per_page") itemsPerPage: Int = 20): Observable<ResponseGitHub>

    @GET("repos/{user}/{repo}/pulls")
    fun getPullRequests(@Path("user") user: String?, @Path("repo") repo: String?): Observable<List<PullRequest>>
}