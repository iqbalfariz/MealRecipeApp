package com.example.mealrecipeapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.room.MealDatabase
import com.example.mealrecipeapp.databinding.ActivityMainBinding
import com.example.mealrecipeapp.ui.MealListAdapter
import com.example.mealrecipeapp.ui.detail.DetailActivity
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
        val database = Room.databaseBuilder(applicationContext, MealDatabase::class.java, "meal_database").allowMainThreadQueries().build()
        val mealDao = database.getMealDao()
        viewModelFactory = ViewModelFactory(MealRecipeRepository(mealDao))
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val mealListAdapter = MealListAdapter()

        mealListAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DATA_ID, selectedData)
            startActivity(intent)
        }

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