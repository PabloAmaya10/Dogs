package com.example.dogs.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.dogs.model.AppViewModel

@Composable
fun ScreenWishListDogs(appModel: AppViewModel) {
    val state = appModel.stateFavorite
    if (state.dogs.isEmpty()) appModel.getDogs()
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        ListTopAppBar("Favoritos")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            items(state.dogs) { dog ->
                Spacer(modifier = Modifier.height(15.dp))
                TarjetaPerro(dog = dog.urlImage, appModel, context, 1)
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}