package com.example.mealrecipeapp.data.remote.api

import com.example.mealrecipeapp.data.remote.response.ListMealAreaResponse
import com.example.mealrecipeapp.data.remote.response.ListTypeFoodResponse
import com.example.mealrecipeapp.data.remote.response.ListMealsDetailResponse
import com.example.mealrecipeapp.data.remote.response.ListMealsResponse
import com.example.mealrecipeapp.data.remote.response.ListTypeAreaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("filter.php")
    suspend fun getTypeCategoryMeals(
        @Query("c") typeCategory: String
    ): ListMealsResponse

    @GET("filter.php")
    suspend fun getAreaCategoryMeals(
        @Query("a") areaCategory: String
    ): ListMealAreaResponse

    @GET("lookup.php")
    suspend fun getDetailsById(
        @Query("i") id: String
    ): ListMealsDetailResponse

    @GET("search.php")
    suspend fun getDataByName(
        @Query("s") nameMeals: String
    ): ListMealsDetailResponse

    @GET("list.php")
    suspend fun getListTypeFood(
        @Query("c") list: String
    ): ListTypeFoodResponse

    @GET("list.php")
    suspend fun getListArea(
        @Query("a") list: String
    ): ListTypeAreaResponse
}