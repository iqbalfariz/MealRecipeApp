package com.example.mealrecipeapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MealByCategoryResponse(

    @SerializedName("strMeal")
    val strMeal: String,

    @SerializedName("strMealThumb")
    val strMealThumb: String,

    @SerializedName("idMeal")
    val idMeal: String
)
