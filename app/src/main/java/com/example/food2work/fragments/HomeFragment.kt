package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.R
import com.example.food2work.RecipeAdapter
import com.example.food2work.RecipeApiService
import com.example.food2work.RecipeModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var recipeRV: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recipeRV = view.findViewById(R.id.idRVRecipe)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeArrayList: ArrayList<RecipeModel> = ArrayList()
        val recipeAdapter = RecipeAdapter(requireContext(), recipeArrayList)

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchRecipes(recipeArrayList,recipeAdapter)

        recipeRV.layoutManager = linearLayoutManager
        recipeRV.adapter = recipeAdapter
    }
    private fun searchRecipes(recipeArrayList: ArrayList<RecipeModel>, recipeAdapter: RecipeAdapter ) {
        val api = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)

        lifecycleScope.launch {
            val response = api.searchRecipes(1, "", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
            if (response?.results != null) {
                val recipeList = response.results.map { result ->
                    val id = result.pk
                    val title = result.title
                    val description = result.description
                    val image = result.featured_image
                    val ingredients = result.ingredients
                    RecipeModel(id, title, description, image, ingredients)
                }
                recipeArrayList.clear()
                recipeArrayList.addAll(recipeList)
                recipeAdapter.notifyDataSetChanged()
            } else {
                // Si la réponse ne contient pas de résultats, on ajoute un objet RecipeModel par défaut
                recipeArrayList.clear()
                recipeArrayList.add(
                    RecipeModel(
                        1,
                        "Pâtes à la Carbonara",
                        "Un plat de pâtes crémeux et savoureux, facile à préparer en quelques minutes.",
                        "https://www.finedininglovers.fr/sites/g/files/xknfdk1291/files/2021-04/pates%20carbonara%20iStock.jpg",
                        listOf("pâtes", "lardons", "œufs", "parmesan", "crème fraîche")
                    )
                )
                recipeAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}
