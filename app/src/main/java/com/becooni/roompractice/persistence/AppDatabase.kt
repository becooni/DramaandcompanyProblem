package com.becooni.roompractice.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.becooni.roompractice.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}