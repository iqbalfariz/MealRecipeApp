package com.example.mealrecipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.remote.response.ListMealAreaResponse
import com.example.mealrecipeapp.data.remote.response.ListTypeFoodResponse
import com.example.mealrecipeapp.data.remote.response.ListMealsDetailResponse
import com.example.mealrecipeapp.data.remote.response.ListMealsResponse
import com.example.mealrecipeapp.data.remote.response.ListTypeAreaResponse
import kotlinx.coroutines.launch

class MainViewModel (private val repository: MealRecipeRepository): ViewModel() {

    val mealsTypeCategoryList = MutableLiveData<ListMealsResponse>()
    val mealsDataByName = MutableLiveData<ListMealsDetailResponse>()
    val listTypeFood = MutableLiveData<ListTypeFoodResponse>()
    val listTypeArea = MutableLiveData<ListTypeAreaResponse>()
    val mealsAreaCategoryList = MutableLiveData<ListMealAreaResponse>()

    fun getMealsByTypeCategory(typeCategory: String){
        viewModelScope.launch {
            mealsTypeCategoryList.value = repository.getTypeCategoryMeals(typeCategory)
        }
    }

    fun getMealsByAreaCategory(areaCategory: String){
        viewModelScope.launch {
            mealsAreaCategoryList.value = repository.getTypeAreaMeals(areaCategory)
        }
    }

    fun getMealsDataByName(name: String){
        viewModelScope.launch {
            mealsDataByName.value = repository.getDataByName(name)
        }
    }

    fun getListCategory(typeFilter: String){
        viewModelScope.launch {
            when (typeFilter) {
                "Type Food" -> {
                    listTypeFood.value = repository.getListTypeFood()
                }
                "Area" -> {
                    listTypeArea.value = repository.getListArea()
                }
                else -> println("none")
            }
        }
    }

}