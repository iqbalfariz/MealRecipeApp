package com.example.mealrecipeapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mealrecipeapp.data.local.enntity.MealEntity
import kotlin.concurrent.Volatile

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
abstract class MealDatabase: RoomDatabase() {

    abstract fun getMealDao(): MealDao


}