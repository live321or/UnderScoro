package com.samoylov.gameproject.locations

import com.google.gson.annotations.SerializedName

data class UserOnLocation(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name:String,
        @SerializedName("username")
        val username: String
)

data class LocationState(
        @SerializedName("id")
        val id: Int,
        @SerializedName("descr")
        val description: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("transitions")
        val transitions: List<String>,
        @SerializedName("users_on_location")
        val users: List<UserOnLocation>

)