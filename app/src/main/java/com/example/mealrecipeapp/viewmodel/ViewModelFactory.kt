package com.example.mealrecipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealrecipeapp.data.MealRecipeRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (private val repository: MealRecipeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }

}