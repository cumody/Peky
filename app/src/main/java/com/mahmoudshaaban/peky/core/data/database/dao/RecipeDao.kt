package com.mahmoudshaaban.peky.core.data.database.dao

import androidx.room.*
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseIngredient
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseInstruction
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseRecipe
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseRecipeInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Transaction
    @Query("SELECT * FROM recipes ORDER BY created_at DESC")
    fun getRecipesWithInformation(): Flow<List<DatabaseRecipeInformation>>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<DatabaseRecipeInformation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(
        recipe: DatabaseRecipe,
        ingredients: List<DatabaseIngredient>,
        instructions: List<DatabaseInstruction>
    )

    @Delete
    suspend fun deleteRecipe(
        recipe: DatabaseRecipe,
        ingredients: List<DatabaseIngredient>,
        instructions: List<DatabaseInstruction>
    )

    @Delete
    suspend fun deleteMultipleRecipes(
        recipes: List<DatabaseRecipe>,
        ingredients: List<DatabaseIngredient>,
        instructions: List<DatabaseInstruction>
    )
}