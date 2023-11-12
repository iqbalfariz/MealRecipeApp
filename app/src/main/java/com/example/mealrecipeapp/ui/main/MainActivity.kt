package com.example.mealrecipeapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.MealRecipeRepository
import com.example.mealrecipeapp.data.local.room.MealDatabase
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import com.example.mealrecipeapp.databinding.ActivityMainBinding
import com.example.mealrecipeapp.ui.MealListAdapter
import com.example.mealrecipeapp.ui.detail.DetailActivity
import com.example.mealrecipeapp.ui.favorite.FavoriteActivity
import com.example.mealrecipeapp.viewmodel.MainViewModel
import com.example.mealrecipeapp.viewmodel.ViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.android.material.search.SearchView


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
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
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

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
        mainViewModel.mealsAreaCategoryList.observe(this){
            val mealAreaList: MutableList<MealByCategoryResponse> = mutableListOf()
            if (it.meals != null){
                for (item in it.meals){
                    val mealList = MealByCategoryResponse(
                        idMeal = item.idMeal,
                        strMeal = item.strMeal,
                        strMealThumb = item.strMealThumb
                    )
                    mealAreaList.add(
                        mealList
                    )
                }
                mealListAdapter.setData(mealAreaList)
            }
        }

        with(binding.rvMealLists) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mealListAdapter
        }

        mainViewModel.mealsDataByName.observe(this){
            println("result search : $it")
            val mealCategoryList: MutableList<MealByCategoryResponse> = mutableListOf()
            if (it != null){
                for (item in it.meals){
                    val mealList = MealByCategoryResponse(
                        idMeal = item.idMeal,
                        strMeal = item.strMeal,
                        strMealThumb = item.strMealThumb
                    )
                    mealCategoryList.add(mealList)
                }
                mealListAdapter.setData(mealCategoryList)
            }
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.getMealsDataByName(query.lowercase())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onResume() {
        super.onResume()
        val typeCategory = resources.getStringArray(R.array.typeCategory)
        val typeCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, typeCategory)
        var typeCategoryResult = ""
        mainViewModel.listTypeFood.observe(this){
            if (it.meals != null){
                val filterListCategory: MutableList<String> = mutableListOf()
                for (item in it.meals){
                    filterListCategory.add(item.strCategory)
                }
                val filterListCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, filterListCategory)
                binding.typeFilterList.setAdapter(filterListCategoryAdapter)
            }
        }
        mainViewModel.listTypeArea.observe(this){
            if (it.meals != null){
                val filterListCategory: MutableList<String> = mutableListOf()
                for (item in it.meals){
                    filterListCategory.add(item.strArea)
                }
                val filterListCategoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, filterListCategory)
                binding.typeFilterList.setAdapter(filterListCategoryAdapter)
            }
        }
        binding.typeCategoryList.setAdapter(typeCategoryAdapter)
        binding.typeCategoryList.setOnItemClickListener { adapterView, view, position, l ->
            mainViewModel.getListCategory(adapterView.getItemAtPosition(position).toString())
            typeCategoryResult = adapterView.getItemAtPosition(position).toString()
        }
        binding.typeFilterList.setOnItemClickListener { adapterView, view, position, l ->
            when (typeCategoryResult) {
                "Type Food" -> {
                    mainViewModel.getMealsByTypeCategory(adapterView.getItemAtPosition(position).toString())
                }
                "Area" -> {
                    mainViewModel.getMealsByAreaCategory(adapterView.getItemAtPosition(position).toString())
                }
                else -> null
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}