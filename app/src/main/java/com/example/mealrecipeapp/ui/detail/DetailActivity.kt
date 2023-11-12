package com.example.mealrecipeapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.enntity.MealEntity
import com.example.mealrecipeapp.data.local.room.MealDatabase
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import com.example.mealrecipeapp.databinding.ActivityDetailBinding
import com.example.mealrecipeapp.viewmodel.DetailViewModel
import com.example.mealrecipeapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val DATA_ID = "data_id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val database = Room.databaseBuilder(applicationContext, MealDatabase::class.java, "meal_database").allowMainThreadQueries().build()
        val mealDao = database.getMealDao()
        viewModelFactory = ViewModelFactory(MealRecipeRepository(mealDao))
        detailViewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        val dataMeals = intent.getParcelableExtra<MealByCategoryResponse>(DATA_ID)
        println("result intent = $dataMeals")

        dataMeals?.idMeal?.let { detailViewModel.getDetailMealsById(it) }

        detailViewModel.detailMealRecipeList.observe(this){
            val item = it.meals[0]
            Glide.with(this)
                .load(item.strMealThumb)
                .into(binding.ivMealThumb)
            binding.tvInstructions.text = item.strInstructions
        }

        binding.fabFavorite.setOnClickListener {
            insertMealData(dataMeals)
        }
    }
    
//    fun MealByCategoryResponse.toMealEntity(): MealEntity {
//        return MealEntity(
//            id = this.idMeal,
//            strMeal = this.strMeal,
//            strMealThumb = this.strMealThumb
//        )
//    }

    fun insertMealData(mealCategories: MealByCategoryResponse?) {
        if (mealCategories != null){
            val mealEntity = MealEntity(
                id = mealCategories.idMeal,
                strMeal = mealCategories.strMeal,
                strMealThumb = mealCategories.strMealThumb
            )
            lifecycleScope.launch {
                detailViewModel.insertFavoriteRecipe(mealEntity)
            }
        }
    }
}