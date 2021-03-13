package com.becooni.roompractice.repository

import com.becooni.roompractice.model.ItemType
import com.becooni.roompractice.model.User
import com.becooni.roompractice.network.GithubClient
import com.becooni.roompractice.persistence.UserDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

// Github에서 사용자를 검색하는 API를 호출 또는 로컬 DB에 있는 데이터에 접근해서 데이터를 가공&제공해주는 클래스
class GithubRepository @Inject constructor(
    private val githubClient: GithubClient,
    private val userDao: UserDao
) {

    // Github에서 사용자를 검색
    // 검색 결과와 Local DB의 즐겨찾기 두 List를 비교하여 즐겨찾기 표시 여부 결정
    // 이때 두 List를 HashMap으로 변환 후 비교하여 성능의 이점을 가져감
    // 즐겨찾기 표시 여부를 체크하고 오름차순으로 Sorting 함
    // Sotring된 List를 각 요소의 첫글자로 Grouping하여 Header + Item으로 구성된 Flatten List를 만듦
    // ex) {ant, apple, big, beef, car, coin} -> {a, ant, apple, b, big, beef, c, car, coin}
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