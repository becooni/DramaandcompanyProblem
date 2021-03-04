package com.becooni.dramaandcompanyproblem.repository

import com.becooni.dramaandcompanyproblem.model.ItemType
import com.becooni.dramaandcompanyproblem.model.User
import com.becooni.dramaandcompanyproblem.network.GithubClient
import com.becooni.dramaandcompanyproblem.persistence.UserDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubClient: GithubClient,
    private val userDao: UserDao
) {

    fun getUsers(
        query: String,
        page: Int = 0,
        perPage: Int = 100,
    ) =
        githubClient.getUsers(query, page, perPage)
            .map { it.users }
            .map { list ->
                list.map { it.copy(initial = it.name.first().toString()) }
            }
            .zipWith(userDao.getBookmarkedUsers()) { users, bookmarkedUsers ->
                if (bookmarkedUsers.isEmpty()) {
                    users
                } else {
                    val userMap = users.associateBy { it.id }.toMutableMap()

                    val bookmarkUserMap = bookmarkedUsers.associateBy { it.id }

                    for (bookmarkUser in bookmarkUserMap) {
                        val key = bookmarkUser.key

                        if (userMap.containsKey(key)) {
                            userMap[key] = bookmarkUser.value
                        }
                    }

                    userMap.values.toList()
                }
            }
            .getSortingList()
            .getGrouping()

    fun getBookmarkUsers(): Single<List<ItemType>> {
        return userDao.getBookmarkedUsers()
            .getSortingList()
            .getGrouping()
    }

    fun getBookmarkUsers(query: String): Single<List<ItemType>> {
        return userDao.getBookmarkedUsers(query)
            .getSortingList()
            .getGrouping()
    }

    fun insertBookmarkUser(item: User): Completable {
        return userDao.insert(item)
    }

    fun deleteBookmarkUser(item: User): Completable {
        return userDao.delete(item)
    }

    private fun Single<List<User>>.getSortingList(): Single<List<User>> {
        return map { list -> list.sortedBy { it.name } }
    }

    private fun Single<List<User>>.getGrouping(): Single<List<ItemType>> {
        return map { list ->
            val typedList = mutableListOf<ItemType>()

            var temp: String? = null

            list.forEach {
                val initial = it.initial
                if (temp != initial) {
                    typedList.add(ItemType.Header(initial))
                    temp = initial
                }
                typedList.add(ItemType.Item(it))
            }
            typedList
        }
    }
}