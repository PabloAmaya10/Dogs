package com.example.dogs.screen

import com.example.dogs.R

sealed class ItemsMenu(
    val icon: Int,
    val title: String,
    val ruta: String
) {
    object ScreenRandomDogs : ItemsMenu(R.drawable.ic_pets, "Inicio", "screenRandomDogs")
    object ScreenBreedDogs : ItemsMenu(R.drawable.ic_search, "Buscar", "screenBreedDogs")
    object ScreenWishListDogs : ItemsMenu(R.drawable.ic_favorite, "Favoritos", "screenWishListDogs")
}
