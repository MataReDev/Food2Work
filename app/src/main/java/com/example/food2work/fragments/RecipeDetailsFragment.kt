package com.example.food2work.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.food2work.R
import com.example.food2work.RecipeModel

class RecipeDetailsFragment(private var recipe: RecipeModel) : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var ingredientsLV: TextView
    private lateinit var dateAdded: TextView
    private lateinit var dateUpdated: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.featured_image)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        ingredientsLV = view.findViewById(R.id.ingredientsLV)
        dateAdded = view.findViewById(R.id.dateAdded)
        dateUpdated = view.findViewById(R.id.dateUpdated)
        detailsRecipe(recipe)
    }

    @SuppressLint("SetTextI18n")
    private fun detailsRecipe(recipe: RecipeModel) {
        var listIngredient: String = ""

        title.text = recipe.title
        description.text = "Description :\n${recipe.description}"
        dateAdded.text = "Ajouté le :\n${recipe.date_added}"
        dateUpdated.text = "Mis à jour :\n${recipe.date_updated}"
        context?.let { loadImage(recipe.featured_image, imageView, it) }
        recipe.ingredients.forEach { ingredient ->
            listIngredient += "${ingredient}\n"
        }
        ingredientsLV.text = listIngredient
    }

    private fun loadImage(url: String?, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_splahscreen) // optional
            .error(R.drawable.ic_splahscreen) // optional
            .into(imageView)
    }
}
