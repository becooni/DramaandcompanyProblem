package com.becooni.dramaandcompanyproblem.network

class GithubClient(
    private val githubService: GithubService
) {

    fun getUsers(
        query: String,
        page: Int = 0,
        perPage: Int = 100,
    ) = githubService.getUsers(query, page, perPage)
}