package com.becooni.dramaandcompanyproblem.repository

import com.becooni.dramaandcompanyproblem.model.User
import com.becooni.dramaandcompanyproblem.network.GithubClient
import com.becooni.dramaandcompanyproblem.persistence.UserDao
import io.reactivex.Completable
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubClient: GithubClient,
    private val userDao: UserDao,
    private val consonantUtil: ConsonantUtil
) {

    fun getUsers(
        query: String,
        page: Int = 0,
        perPage: Int = 100,
    ) =
        githubClient.getUsers(query, page, perPage)
            .map { it.users }
            .zipWith(userDao.getBookmarkedUsers()) { users, bookmarkedUsers ->
                if (bookmarkedUsers.isEmpty()) {
                    users
                } else {
                    val userMap = users.associateBy { it.id }
                        .toMutableMap()

                    val bookmarkUserMap = bookmarkedUsers.associateBy { it.id }

                    for (bookmarkUser in bookmarkUserMap) {
                        val key = bookmarkUser.key
                        if (userMap[key]?.id == bookmarkUser.value.id) {
                            userMap[key] = bookmarkUser.value
                        }
                    }

                    userMap.values.toList()
                }
            }
            .map { list -> list.sortedBy { it.name } }
            .map { list ->
                list.map { it.apply { initialConsonant = consonantUtil.getInitial(it.name) } }
            }

    fun getBookmarkUsers() = userDao.getBookmarkedUsers()

    fun insertBookmarkUser(item: User): Completable {
        item.bookmarked = true
        return userDao.insert(item)
    }
}