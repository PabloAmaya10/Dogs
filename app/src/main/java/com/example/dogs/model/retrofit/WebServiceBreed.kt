package com.example.dogs.model.retrofit

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/*url base del web service*/
const val URL_BASE_BREED = "https://dog.ceo/api/breed/"
const val BREED_DOGS = "{breed}/images/random/{numberDogs}"
const val SUB_BREED_DOGS= "{breed}/{subBreed}/images/random/{numberDogs}"


/*Interface usada para mapear los metodos
* que usaremos del servecio web
* GET,POST,PUT,DELATE*/
@JsonClass(generateAdapter = true)
interface BreedDogService {
    @GET(BREED_DOGS)
    suspend fun getDogsByBreed(
        @Path("breed") breed: String,
        @Path("numberDogs") numberDogs: String
    ): ResponseDog

    @GET(SUB_BREED_DOGS)
    suspend fun getDogsBySubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String,
        @Path("numberDogs") numberDogs: String
    ): ResponseDog
}

/*Creamos la clase para conectarnos al servicio web
* usando retrofit*/

object RetrofitInstanceBreed {
    /*declaramos a Moshi (Consersor JSON -> CLASS)*/
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /*Declarar la coneccion con retrofit*/
    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(URL_BASE_BREED)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /*Servicio externo de retrofit*/
    val Service: BreedDogService by lazy {
        retrofit.create(BreedDogService::class.java)
    }
}
