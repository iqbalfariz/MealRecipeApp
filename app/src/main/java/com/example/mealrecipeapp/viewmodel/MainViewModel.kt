package com.example.mealrecipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.remote.response.ListMealsResponse
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response

class MainViewModel (private val repository: MealRecipeRepository): ViewModel() {

    val mealsTypeCategoryList = MutableLiveData<ListMealsResponse>()

    fun getMealsByTypeCategory(typeCategory: String){
        viewModelScope.launch {
            mealsTypeCategoryList.value = repository.getTypeCategoryMeals(typeCategory)
        }
    }

}