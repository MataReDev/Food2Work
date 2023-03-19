package com.example.food2work

import Favori
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView



class FavoriteRecipeAdapter : ListAdapter<RecipeModel, FavoriteRecipeAdapter.FavoriteViewHolder>(FavoritesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title)
    }

    fun setData(favoris: List<Favori>?) {

    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favoriteTitleCardView: TextView = itemView.findViewById(R.id.titleRecipe)

        fun bind(text: String?) {
            favoriteTitleCardView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): FavoriteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view, parent, false)
                return FavoriteViewHolder(view)
            }
        }
    }

    class FavoritesComparator : DiffUtil.ItemCallback<RecipeModel>() {
        override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
            return oldItem.title == newItem.title
        }
    }
}