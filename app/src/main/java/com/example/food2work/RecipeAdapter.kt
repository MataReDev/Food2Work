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


class RecipeAdapter(private val context: Context, private val recipeModelArrayList: ArrayList<RecipeModel>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
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
        // this method is used for showing number of card items in recycler view.
        return recipeModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
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
