package com.example.dogs.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dogs.model.room.DBRoom
import com.example.dogs.model.room.Dog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException

/*En este archivo crearemos
* view model que nos ayude a enviar los datos
* del webservice a nuestra vista*/

data class DataState(
    val dogs: List<String> = arrayListOf()
)

data class DataStateFavorite(
    val dogs: List<Dog> = arrayListOf()
)

data class DataStateDogsByBreed(
    val dogs: List<String> = arrayListOf()
)

data class DataStateBreeds(
    val expanded: Boolean = false,
    val listBreeds: List<String> = arrayListOf(),
    val selectedItem: String = "",
    val texFiledSize: Size = Size.Zero
)

/*View Model para la aplicación*/
class AppViewModel(context: Context) : ViewModel() {
    /*Iniciamos el repositorio de datos*/
    private var repository: DogRepository? = null
    private val scope: CoroutineScope = viewModelScope /*se usa para la creaccion de corrutinas*/
    var state by mutableStateOf(DataState())
        private set

    var stateFavorite by mutableStateOf(DataStateFavorite())
        private set

    var stateDogsByBreed by mutableStateOf(DataStateDogsByBreed())
        private set

    var stateBreeds by mutableStateOf(DataStateBreeds())
        private set

    init {
        initRepository(context = context)
    }

    private fun initRepository(context: Context) {
        val dataBase = DBRoom.getDatabase(context = context)
        repository = DogRepository(dataBase.dogsDao())
    }

    /*Este metodo modifica el estado de la lista de dogs usando
    * los datos del repositorio*/
    fun changeStateDogs(numberDogs: Int) {
        try {
            if (numberDogs > 0) {
                scope.launch(Dispatchers.IO) {
                    val dogs = repository?.getDogs(numberDogs.toString())
                    if (dogs?.status == "success") {
                        state = state.copy(dogs = dogs.message)
                    } else {
                        throw RuntimeException("Error no se encontraron los perros")
                    }
                }
            } else {
                throw RuntimeException("Elementos vacios")
            }
        } catch (ex: Exception) {
            Log.e("ErrorDogs", ex.message.toString())
        }
    }

    fun changeStateListBreeds() {
        scope.launch(Dispatchers.IO) {
            val dogs = repository?.getAllBreed()
            if (dogs?.status == "success") {
                val breeds = arrayListOf<String>()
                dogs.message.entries.forEach { entry ->
                    //construimos el listado de razas de perro
                    if (entry.value.isNotEmpty()) {
                        entry.value.forEach {
                            breeds.add("${entry.key}/${it}")
                        }
                    } else {
                        breeds.add(entry.key)
                    }
                }
                stateBreeds = stateBreeds.copy(listBreeds = breeds)
            } else {
                throw RuntimeException("Error no se encontraron los perros")
            }
        }
    }

    fun changeStateExpanded(value: Boolean) {
        stateBreeds = stateBreeds.copy(
            expanded = value
        )
    }

    fun changeStateSelectedItem(value: String) {
        stateBreeds = stateBreeds.copy(
            selectedItem = value
        )
    }

    fun changeStateTexFiledSize(value: Size) {
        stateBreeds = stateBreeds.copy(
            texFiledSize = value
        )
    }

    //Cards de perros por raza
    fun changeStateDogsByBreed(breed: String, numberDogs: Int) {
        try {
            scope.launch(Dispatchers.IO) {
                val listBreeds = breed.split("/")
                val dogs = when (listBreeds.size) {
                    1 -> {
                        repository?.getDogsByBreed(listBreeds.first(), numberDogs.toString())
                    }

                    2 -> {
                        repository?.getDogsBySubBreed(
                            listBreeds.first(),
                            listBreeds.last(),
                            numberDogs.toString()
                        )
                    }
                    else -> {
                        throw RuntimeException("Error no se encontraron los perros")
                    }

                }
                if (dogs?.status == "success") {
                    stateDogsByBreed = stateDogsByBreed.copy(dogs = dogs.message)
                } else {
                    Log.e("ErrorDogs", "Error no se encontraron los perros por raza")
                }
            }

        } catch (ex: Exception) {
            Log.e("ErrorDogs", ex.message.toString())
        }
    }

    private fun changeStateListDog(dogs: List<Dog>) {
        stateFavorite = stateFavorite.copy(
            dogs = dogs.sortedWith(compareBy<Dog> {
                it.id
            }.reversed())
        )
    }

    fun getDogs() {
        scope.launch(Dispatchers.IO) {
            repository?.getAllDogs()?.collect {
                changeStateListDog(it)
            }
        }
    }

    fun insertDog(urlImage: String) {
        scope.launch(Dispatchers.IO) {
            getDogs()
            if (stateFavorite.dogs.find { it.urlImage == urlImage } == null) {
                repository?.insertDog(Dog(urlImage = urlImage))
                getDogs()
            }
        }
    }
}


class Fabrica(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(context = context) as T
    }
}