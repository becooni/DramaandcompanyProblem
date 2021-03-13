package com.becooni.roompractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val bookmarked: Boolean,
    val initial: String
)
