package com.example.food2work

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

/**
 * ApiService
 */
interface RecipeApiService {
    @GET("recipe/search/")
    suspend fun searchRecipes(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Header("Authorization") token: String
    ): ApiResponseMultiple

    @GET("recipe/get/")
    suspend fun searchRecipeById(
        @Query("id") id: Int,
        @Header("Authorization") token: String
    ) : RecipeModel
}


data class ApiResponseMultiple(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeModel>
)



