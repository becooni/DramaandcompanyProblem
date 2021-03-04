package com.becooni.dramaandcompanyproblem.network

import com.becooni.dramaandcompanyproblem.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/search/users")
    fun getUsers(
        @Query("q")
        query: String,
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
    ): Single<UserResponse>
}