package com.example.public_apis_list_showcase.data.remote.models

import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("API")
    val api: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Auth")
    val auth: String,
    @SerializedName("HTTPS")
    val https: Boolean,
    @SerializedName("Cors")
    val cors: String,
    @SerializedName("Link")
    val link: String,
    @SerializedName("Category")
    val category: String
)
