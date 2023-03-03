package com.example.food2work

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * ApiService
 */
interface RecipeApiService {
    @GET("recipe/search/")
    suspend fun searchRecipes(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Header("Authorization") token: String
    ): ApiResponse
}


data class ApiResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<RecipeModel>
)


