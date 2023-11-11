package com.example.mealrecipeapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.databinding.ActivityMainBinding
import com.example.mealrecipeapp.ui.MealListAdapter
import com.example.mealrecipeapp.viewmodel.MainViewModel
import com.example.mealrecipeapp.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModelFactory = ViewModelFactory(MealRecipeRepository())
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val mealListAdapter = MealListAdapter()

        mainViewModel.getMealsByTypeCategory("Beef")
        mainViewModel.mealsTypeCategoryList.observe(this){
            if (it.meals != null){
                mealListAdapter.setData(it.meals)
            }
        }

        with(binding.rvMealLists) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mealListAdapter
        }
    }
}