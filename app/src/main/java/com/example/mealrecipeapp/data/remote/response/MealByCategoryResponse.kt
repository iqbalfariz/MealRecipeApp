package com.example.mealrecipeapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MealByCategoryResponse(

    @SerializedName("strMeal")
    val strMeal: String,

    @SerializedName("strMealThumb")
    val strMealThumb: String,

    @SerializedName("idMeal")
    val idMeal: String
) : Parcelable
