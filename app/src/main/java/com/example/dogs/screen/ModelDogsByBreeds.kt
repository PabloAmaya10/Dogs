package com.example.dogs.screen

import androidx.compose.ui.geometry.Size


data class ModelDogsByBreeds(
    val expanded : Boolean = false,
    val listBreeds : List<String>,
    val selectedItem: String = "",
    val texFiledSize: Size = Size.Zero
)