package com.example.dogs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dogs.model.AppViewModel
import com.example.dogs.model.Fabrica
import com.example.dogs.screen.ItemsMenu
import com.example.dogs.screen.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Registrar e inicar el view Model*/
        val appModel by viewModels<AppViewModel>() {
            Fabrica(this)
        }
        setContent {
            //ScreenMain(appModel)
            ScreenController(appViewModel = appModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenController(appViewModel: AppViewModel) {
    val navController: NavHostController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val navigationItem =
        listOf(
            ItemsMenu.ScreenRandomDogs,
            ItemsMenu.ScreenBreedDogs,
            ItemsMenu.ScreenWishListDogs
        )
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = { NavegacionInferior(navController, navigationItem) }) {
        NavigationHost(navController = navController, appModel = appViewModel)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route
}


@Composable
fun NavegacionInferior(
    navController: NavHostController,
    menuItems: List<ItemsMenu>
) {
    NavigationBar() {
        menuItems.forEach { item ->
            val selected = item.ruta == currentRoute(navController = navController)
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.ruta) },
                label = {
                    Text(text = item.title, fontWeight = FontWeight.SemiBold)
                }, icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}

