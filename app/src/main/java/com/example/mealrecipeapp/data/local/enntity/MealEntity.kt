package com.example.mealrecipeapp.data.local.enntity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "mealEntity")
data class MealEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "idMeal")
    var id: String,

    @ColumnInfo(name = "strMeal")
    var strMeal: String,

    @ColumnInfo(name = "strMealThumb")
    var strMealThumb: String
)
