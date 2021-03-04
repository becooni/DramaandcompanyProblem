package com.becooni.dramaandcompanyproblem.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.becooni.dramaandcompanyproblem.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getBookmarkedUsers(): Single<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE '%' || :query || '%'")
    fun getBookmarkedUsers(query: String): Single<List<User>>

    @Insert
    fun insert(vararg items: User): Completable

    @Delete
    fun delete(vararg items: User): Completable
}