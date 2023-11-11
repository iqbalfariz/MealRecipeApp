package com.example.mealrecipeapp.data.remote.api

import com.example.mealrecipeapp.data.remote.response.ListMealsResponse
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("filter.php")
    suspend fun getTypeCategoryMeals(
        @Query("c") typeCategory: String
    ): ListMealsResponse

}