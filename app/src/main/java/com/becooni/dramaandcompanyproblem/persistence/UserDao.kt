package com.becooni.dramaandcompanyproblem.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.becooni.dramaandcompanyproblem.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE bookmarked = 1")
    fun getBookmarkedUsers(): Single<List<User>>

    @Insert
    fun insert(vararg items: User): Completable

    @Update
    fun update(vararg items: User): Completable
}