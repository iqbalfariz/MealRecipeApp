package com.example.mealrecipeapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.room.MealDatabase
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import com.example.mealrecipeapp.databinding.ActivityFavoriteBinding
import com.example.mealrecipeapp.ui.MealListAdapter
import com.example.mealrecipeapp.ui.detail.DetailActivity
import com.example.mealrecipeapp.viewmodel.DetailViewModel
import com.example.mealrecipeapp.viewmodel.FavoriteViewModel
import com.example.mealrecipeapp.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val database = Room.databaseBuilder(applicationContext, MealDatabase::class.java, "meal_database").allowMainThreadQueries().build()
        val mealDao = database.getMealDao()
        viewModelFactory = ViewModelFactory(MealRecipeRepository(mealDao))
        favoriteViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
        val mealListAdapter = MealListAdapter()

        mealListAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DATA_ID, selectedData)
            startActivity(intent)
        }

        favoriteViewModel.allFavoriteRecipe.observe(this){
            val mealCategoryList: MutableList<MealByCategoryResponse> = mutableListOf()
            if (it != null){
                for (item in it){
                    val mealList = MealByCategoryResponse(
                        idMeal = item.id,
                        strMeal = item.strMeal,
                        strMealThumb = item.strMealThumb
                    )
                    mealCategoryList.add(mealList)
                }
                mealListAdapter.setData(mealCategoryList)
            }
        }

        with(binding.rvMealFavoriteLists) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter= mealListAdapter
        }
    }
}