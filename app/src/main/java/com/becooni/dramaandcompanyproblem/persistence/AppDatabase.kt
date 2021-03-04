package com.becooni.dramaandcompanyproblem.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.becooni.dramaandcompanyproblem.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}