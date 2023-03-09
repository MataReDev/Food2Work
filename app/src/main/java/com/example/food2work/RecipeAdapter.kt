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


class RecipeAdapter(
    private val context: Context,
    private val recipeModelArrayList: ArrayList<RecipeModel>,
    private val listener: OnRecipeItemClickListener?,
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
        val model: RecipeModel = recipeModelArrayList[position]
        holder.titleRecipe.text = model.title
        holder.descriptionRecipe.text = model.description
        holder.nbIngredientRecipe.text = "Nb Ingrédients : ${model.ingredients.size}"
        loadImage(model.featured_image, holder.imageLinkRecipe)
    }

    override fun getItemCount(): Int {
        return recipeModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageLinkRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)
        val titleRecipe: TextView = itemView.findViewById(R.id.titleRecipe)
        val descriptionRecipe: TextView = itemView.findViewById(R.id.descriptionRecipe)
        val nbIngredientRecipe: TextView = itemView.findViewById(R.id.nbIngredientRecipe)
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