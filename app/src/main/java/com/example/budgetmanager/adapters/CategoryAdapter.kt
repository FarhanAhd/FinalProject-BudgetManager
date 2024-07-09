package com.example.budgetmanager.adapters

import com.example.budgetmanager.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.databinding.SampleCategoryItemBinding
import com.example.budgetmanager.models.Category

class CategoryAdapter(
    var context: Context, categories: ArrayList<Category>?,
    var categoryClickListener: CategoryClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var categories: ArrayList<Category>? = categories

    interface CategoryClickListener {
        fun onCategoryClicked(category: Category?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.sample_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = categories!![position]
        holder.binding.categoryText.setText(category.getCategoryName())
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage())

        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()))

        holder.itemView.setOnClickListener { c: View? ->
            categoryClickListener.onCategoryClicked(category)
        }
    }

    override fun getItemCount(): Int {
        return categories!!.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SampleCategoryItemBinding = SampleCategoryItemBinding.bind(itemView)
    }
}