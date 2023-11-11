package com.example.mealrecipeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealrecipeapp.R
import com.example.mealrecipeapp.data.remote.response.MealByCategoryResponse
import com.example.mealrecipeapp.databinding.MealListItemBinding

class MealListAdapter: RecyclerView.Adapter<MealListAdapter.ListViewHolder>() {

    private var listData = ArrayList<MealByCategoryResponse>()
    var onItemClick: ((MealByCategoryResponse) -> Unit)? = null

    fun setData(newListData: List<MealByCategoryResponse>?){
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal_list_item, parent, false))

    override fun onBindViewHolder(holder: MealListAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
       private val binding = MealListItemBinding.bind(itemView)
//        val baseUrl = "https://www.themealdb.com/images/media/meals/"

        fun bind(data: MealByCategoryResponse){
            with(binding){
                Glide.with(itemView.context)
                    .load(data.strMealThumb)
                    .into(ivMeal)
                tvMeal.text= data.strMeal
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }
}