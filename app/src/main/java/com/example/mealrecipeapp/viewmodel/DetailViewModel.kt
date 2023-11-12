package com.example.mealrecipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.enntity.MealEntity
import com.example.mealrecipeapp.data.remote.response.ListMealsDetailResponse
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: MealRecipeRepository): ViewModel() {

    val detailMealRecipeList = MutableLiveData<ListMealsDetailResponse>()

    fun getDetailMealsById(id: String){
        viewModelScope.launch {
            detailMealRecipeList.value = repository.getDetailsById(id)
        }
    }

    fun insertFavoriteRecipe(mealEntity: MealEntity){
        viewModelScope.launch {
            repository.insertFavoriteRecipe(mealEntity)
        }
    }

}