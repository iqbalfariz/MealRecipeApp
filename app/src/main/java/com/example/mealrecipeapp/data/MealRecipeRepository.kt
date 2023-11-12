package com.example.mealrecipeapp.data

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.mealrecipeapp.data.local.enntity.MealEntity
import com.example.mealrecipeapp.data.local.room.MealDao
import com.example.mealrecipeapp.data.local.room.MealDatabase
import com.example.mealrecipeapp.data.remote.api.ApiConfig
import com.example.mealrecipeapp.data.remote.api.ApiService
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse

class MealRecipeRepository(private val mealDao: MealDao) {

    private val apiService: ApiService = ApiConfig.apiService
    suspend fun getTypeCategoryMeals(typeCategory: String) = apiService.getTypeCategoryMeals(typeCategory)

    suspend fun getDetailsById(id: String) = apiService.getDetailsById(id)

    suspend fun getFavoriteRecipe(){
        mealDao.getFavoriteMealRecipe()
    }

    suspend fun insertFavoriteRecipe(mealEntity: MealEntity){
        mealDao.insertMealData(mealEntity)
    }



}