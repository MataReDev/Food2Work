package com.example.food2work

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food2work.database.RecipeFavorisDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecipeAdapter(
    private val context: Context,
    private val recipeModelArrayList: ArrayList<RecipeModel>,
    private val listener: OnRecipeItemClickListener?,
    private val recipeFavorisDao: RecipeFavorisDao,
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onRecipeItemClick(recipeModelArrayList[position])
            }
        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe: RecipeModel = recipeModelArrayList[position]
        holder.titleRecipe.text = recipe.title
        holder.descriptionRecipe.text = recipe.description
        holder.nbIngredientRecipe.text = "Nb Ingrédients : ${recipe.ingredients.size}"
        loadImage(recipe.featured_image, holder.imageLinkRecipe)
        holder.favoriteIV.setImageResource(R.drawable.ic_star)
        holder.favoriteIV.setOnClickListener {
            // Toggle the state of the ImageView and change the image accordingly
            holder.favoriteIV.isSelected = !holder.favoriteIV.isSelected
            if (holder.favoriteIV.isSelected) {
                holder.favoriteIV.setImageResource(R.drawable.star_1_svgrepo_com)
                // Add the recipe to favorites
                CoroutineScope(Dispatchers.IO).launch {
                    recipeFavorisDao.insert(recipe)
                    println( recipeFavorisDao.getRecipeCount())
                }
            } else {
                holder.favoriteIV.setImageResource(R.drawable.ic_star)
                CoroutineScope(Dispatchers.IO).launch {
                    recipeFavorisDao.delete(recipe)
                }
            }
        }
        holder.itemView.setOnClickListener {
            listener?.onRecipeItemClick(recipe)
        }
    }

    override fun getItemCount(): Int {
        return recipeModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageLinkRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)
        val titleRecipe: TextView = itemView.findViewById(R.id.titleRecipe)
        val descriptionRecipe: TextView = itemView.findViewById(R.id.descriptionRecipe)
        val nbIngredientRecipe: TextView = itemView.findViewById(R.id.nbIngredientRecipe)
        val favoriteIV: ImageButton = itemView.findViewById(R.id.starButton)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_splahscreen) // optional
            .error(R.drawable.ic_splahscreen) // optional
            .into(imageView)
    }
}

interface OnRecipeItemClickListener {
    fun onRecipeItemClick(recipe: RecipeModel)
}