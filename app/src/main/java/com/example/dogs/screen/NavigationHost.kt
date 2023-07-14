package com.example.dogs.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dogs.model.AppViewModel
import com.example.dogs.screen.ItemsMenu.*

@Composable
fun NavigationHost(navController: NavHostController, appModel: AppViewModel) {
    NavHost(
        navController = navController,
        startDestination = ScreenRandomDogs.ruta
    ) {
        composable(ScreenRandomDogs.ruta) {
            ScreenMain(appModel = appModel)
        }
        composable(ScreenBreedDogs.ruta) {
            ScreenBreedDogs(appModel = appModel)
        }
        composable(ScreenWishListDogs.ruta) {
            ScreenWishListDogs(appModel = appModel)
        }
    }
}