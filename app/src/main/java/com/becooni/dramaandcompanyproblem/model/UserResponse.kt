package com.becooni.dramaandcompanyproblem.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    val users: List<User>
)