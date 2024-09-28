// RetrofitService.kt
package com.example.myaplicacion.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Definir la interfaz para la API de Fakestore
interface FakestoreApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}

// Data class para representar los productos
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

// Configurar Retrofit
object RetrofitService {
    private const val BASE_URL = "https://fakestoreapi.com/"

    val api: FakestoreApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakestoreApiService::class.java)
    }
}
