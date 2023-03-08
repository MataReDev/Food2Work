package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.R
import com.example.food2work.RecipeApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeDetailsFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var ingredientRV: RecyclerView
    private lateinit var date_added: TextView
    private lateinit var date_updated: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.featured_image)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        ingredientRV = view.findViewById(R.id.ingredients_recycler_view)
        date_added = view.findViewById(R.id.date_added)
        date_updated = view.findViewById(R.id.date_updated)

        val id: Int = 1
        searchRecipe(id)
    }


    private fun searchRecipe(id: Int) {
        val api = Retrofit.Builder().baseUrl("https://food2fork.ca/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RecipeApiService::class.java)

        lifecycleScope.launch {
            val response = api.searchARecipe(id, "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
            if (response?.recipe != null) {
                val recipe = response.recipe
                title.text = recipe.title
                description.text = recipe.description
                date_added.text = recipe.date_added
                date_updated.text = recipe.date_updated

                // Set the recipe image
//                imageView(featured_image)
//
//
//
//                // Update the RecyclerView adapter with the list of ingredients
//                ingredientRV.adapter = IngredientsAdapter(ingredients)
            }
        }
    }

}