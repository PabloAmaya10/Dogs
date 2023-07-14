package com.example.dogs.model.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseListBreeds(
    @field:Json(name = "message")
    val message: Map<String, List<String>>,
    @field:Json(name = "status")
    val status: String,
    )
