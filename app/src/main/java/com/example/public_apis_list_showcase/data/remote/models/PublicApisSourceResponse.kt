package com.example.public_apis_list_showcase.data.remote.models

data class PublicApisSourceResponse(
    val count: Int,
    val entries: List<Entry> = emptyList()
)
