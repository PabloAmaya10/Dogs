package com.example.dogs.model.retrofit

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/*url base del web service*/
const val URL_BASE = "https://dog.ceo/api/breeds/"
const val RANDOM_DOGS = "image/random/{numberDogs}"
const val LIST_BREEDS = "list/all"


/*Interface usada para mapear los metodos
* que usaremos del servecio web
* GET,POST,PUT,DELATE*/
@JsonClass(generateAdapter = true)
interface DogService {
    @GET(RANDOM_DOGS)
    suspend fun getDogs(
        @Path("numberDogs") numberDogs: String
    ): ResponseDog
    @GET(LIST_BREEDS)
    suspend fun getAllBreeds(): ResponseListBreeds
}

/*Creamos la clase para conectarnos al servicio web
* usando retrofit*/

object RetrofitInstance {
    /*declaramos a Moshi (Consersor JSON -> CLASS)*/
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /*Declarar la coneccion con retrofit*/
    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /*Servicio externo de retrofit*/
    val Service: DogService by lazy {
        retrofit.create(DogService::class.java)
    }
}
