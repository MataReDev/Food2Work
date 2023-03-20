package com.example.food2work.database

import androidx.room.*
import com.example.food2work.RecipeModel

@Dao
interface RecipeFavorisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeModel: RecipeModel)

    @Delete
    fun delete(recipeModel: RecipeModel)

    @Query("SELECT * FROM recettes_favoris")
    fun getAll(): List<RecipeModel>

    @Query("SELECT COUNT(*) FROM recettes_favoris")
    fun getRecipeCount(): Int

}
