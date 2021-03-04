package com.becooni.dramaandcompanyproblem.model

import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "bookmarked", typeAffinity = ColumnInfo.INTEGER)
    var bookmarked: Boolean,
    var initialConsonant: Char?
)
