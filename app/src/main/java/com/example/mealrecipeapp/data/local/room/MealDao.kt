package com.example.mealrecipeapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealrecipeapp.data.local.enntity.MealEntity


@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMealData(mealEntity: MealEntity)

    @Query("SELECT * FROM mealEntity")
    fun getFavoriteMealRecipe(): LiveData<List<MealEntity>>


}