package com.becooni.dramaandcompanyproblem.persistence

import androidx.room.*
import com.becooni.dramaandcompanyproblem.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE bookmarked = 1")
    fun getBookmarkedUsers(): Single<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: User): Completable

    @Update
    fun update(vararg items: User): Completable
}