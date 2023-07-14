package com.example.dogs.model.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResponseDog(
    @field:Json(name = "message")
    val message: List<String>,
    @field:Json(name = "status")
    val status : String
)