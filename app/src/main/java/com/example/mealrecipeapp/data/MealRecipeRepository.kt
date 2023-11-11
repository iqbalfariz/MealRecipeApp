package com.example.mealrecipeapp.data

import com.example.mealrecipeapp.data.remote.api.ApiConfig
import com.example.mealrecipeapp.data.remote.api.ApiService
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse

class MealRecipeRepository() {

    private val apiService: ApiService = ApiConfig.apiService
    suspend fun getTypeCategoryMeals(typeCategory: String) = apiService.getTypeCategoryMeals(typeCategory)

}