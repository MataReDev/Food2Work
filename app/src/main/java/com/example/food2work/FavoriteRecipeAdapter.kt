package com.example.food2work

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class FavoriteRecipeAdapter(
    private val context: Context,
    private val favoriteRecipeArrayList: List<RecipeModel>,
    private val listener: OnFavoriteRecipeItemClickListener?
) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        val viewHolder = FavoriteViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onFavoriteRecipeItemClick(favoriteRecipeArrayList[position])
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return favoriteRecipeArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite: RecipeModel = favoriteRecipeArrayList[position]
        holder.titleRecipe.text = favorite.title
        holder.descriptionRecipe.text = favorite.description
        holder.nbIngredientRecipe.text = "Nb Ingrédients : ${favorite.ingredients.size}"
        loadImage(favorite.featured_image, holder.imageLinkRecipe)

    }
    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_splahscreen) // optional
            .error(R.drawable.ic_splahscreen) // optional
            .into(imageView)
    }


    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageLinkRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)
        val titleRecipe: TextView = itemView.findViewById(R.id.titleRecipe)
        val descriptionRecipe: TextView = itemView.findViewById(R.id.descriptionRecipe)
        val nbIngredientRecipe: TextView = itemView.findViewById(R.id.nbIngredientRecipe)
    }
}

interface OnFavoriteRecipeItemClickListener {
    fun onFavoriteRecipeItemClick(recipe: RecipeModel)
}