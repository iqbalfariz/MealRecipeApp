package com.example.mealrecipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.enntity.MealEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: MealRecipeRepository): ViewModel() {

    val allFavoriteRecipe: LiveData<List<MealEntity>> = repository.allFavoriteRecipe

}