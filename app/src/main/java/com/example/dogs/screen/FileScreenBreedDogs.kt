package com.example.dogs.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.dogs.model.AppViewModel
import com.example.dogs.model.DataStateBreeds
import com.example.dogs.model.DataStateDogsByBreed

@Composable
fun ScreenBreedDogs(appModel: AppViewModel) {
    val state = appModel.stateBreeds
    val stateDogsByBreed = appModel.stateDogsByBreed
    val context = LocalContext.current
    if (state.listBreeds.isEmpty()) appModel.changeStateListBreeds()
    dropDownMenu(appModel = appModel, state = state, stateDogsByBreed,context)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(
    appModel: AppViewModel,
    state: DataStateBreeds,
    stateDogsByBreed: DataStateDogsByBreed,
    context:Context
) {
    val icon = if (state.expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        ListTopAppBar("Perros por raza")
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = state.selectedItem,
            onValueChange = { appModel.changeStateSelectedItem(it) },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { appModel.changeStateTexFiledSize(it.size.toSize()) },
            label = { Text(text = "Selecciona la raza") },
            trailingIcon = {
                Icon(
                    icon,
                    "",
                    Modifier.clickable { appModel.changeStateExpanded(!state.expanded) })
            }

        )

        DropdownMenu(
            expanded = state.expanded,
            onDismissRequest = { appModel.changeStateExpanded(false) },
            modifier = Modifier.width(with(LocalDensity.current) { state.texFiledSize.width.dp })
        ) {
            state.listBreeds.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        appModel.changeStateDogsByBreed(label,6)
                        appModel.changeStateSelectedItem(label)
                        appModel.changeStateExpanded(false)
                    })
            }

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            items(stateDogsByBreed.dogs) { dog ->
                Spacer(modifier = Modifier.height(15.dp))
                TarjetaPerro(dog = dog, appModel, context)
            }
        }
    }

}