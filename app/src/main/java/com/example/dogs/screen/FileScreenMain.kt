package com.example.dogs.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dogs.R
import com.example.dogs.model.AppViewModel

@Composable
fun ScreenMain(appModel: AppViewModel) {
    val state = appModel.state
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        ListTopAppBar("Perros")
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            items(state.dogs) { dog ->
                Spacer(modifier = Modifier.height(15.dp))
                TarjetaPerro(dog = dog, appModel, context)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { appModel.changeStateDogs(6) }) {
                Text(text = "Obtener Perros")
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListTopAppBar(title: String) {
    TopAppBar(title = { Text(text = title) }
    )
}

@Composable

fun TarjetaPerro(dog: String, appModel: AppViewModel, context: Context, screen: Int = 0) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RectangleShape)
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = dog,
                    contentDescription = "perro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(30.dp))
                IconButton(onClick = {
                    if (screen == 0) {
                        appModel.insertDog(dog)
                        Toast.makeText(
                            context,
                            "Agregado exitosamente a favoritos",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }else{
                        //eliminar
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(92.dp),
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Favorites",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    ScreenMain(appModel = AppViewModel(LocalContext.current))
}