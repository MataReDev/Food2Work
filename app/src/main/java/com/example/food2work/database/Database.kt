package com.example.food2work.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.food2work.RecipeModel

@Database(entities = [RecipeModel::class], version = 1)
@TypeConverters(value = [StringListConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriDao(): RecipeFavorisDao

    companion object {
        private const val DATABASE_NAME = "food2work_database"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        if (context == null) {
                            throw IllegalStateException("Context cannot be null")
                        }
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return instance!!
        }

    }
}