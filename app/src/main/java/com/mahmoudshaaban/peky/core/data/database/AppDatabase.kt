package com.mahmoudshaaban.peky.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudshaaban.peky.core.constants.K
import com.mahmoudshaaban.peky.core.data.database.dao.RecipeDao
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseIngredient
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseInstruction
import com.mahmoudshaaban.peky.core.data.database.entity.DatabaseRecipe

@Database(
    version = 2,
    exportSchema = false,
    entities = [DatabaseRecipe::class, DatabaseIngredient::class, DatabaseInstruction::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            K.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}