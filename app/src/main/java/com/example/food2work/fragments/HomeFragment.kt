package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnRecipeItemClickListener {
    private lateinit var recipeRV: RecyclerView
    private lateinit var searchSV: SearchView
    private var pageRecipe: Int = 1
    private val token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recipeRV = view.findViewById(R.id.idRVRecipe)
        searchSV = view.findViewById(R.id.SVrecipe)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeArrayList: ArrayList<RecipeModel> = ArrayList()
        val recipeAdapter = RecipeAdapter(requireContext(), recipeArrayList, this)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchRecipes(recipeArrayList, recipeAdapter)

        recipeRV.layoutManager = linearLayoutManager
        recipeRV.adapter = recipeAdapter

        searchSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                recipeArrayList.clear()
                pageRecipe = 1
                searchRecipes(recipeArrayList, recipeAdapter)
                return false
            }

        })

        recipeRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.itemCount-1* pageRecipe) {
                    pageRecipe ++
                    searchRecipes(recipeArrayList, recipeAdapter)
                }
                super.onScrolled(recipeRV, dx, dy)
            }
        })
    }

    private fun searchRecipes(
        recipeArrayList: ArrayList<RecipeModel>,
        recipeAdapter: RecipeAdapter,
    ) {
        val api = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)

        lifecycleScope.launch {
            val response =
                api.searchRecipes(
                    pageRecipe,
                    searchSV.query.toString(),
                    token
                )
            if (response?.recipes != null) {
                val recipeList = response.recipes.map { result ->
                    val id = result.pk
                    val title = result.title
                    val description = result.description
                    val image = result.featured_image
                    val ingredients = result.ingredients
                    val dateAdded = result.date_added
                    val dateUpdated = result.date_updated
                    RecipeModel(
                        id,
                        title,
                        description,
                        image,
                        ingredients,
                        dateAdded,
                        dateUpdated
                    )
                }
                recipeArrayList.addAll(recipeList)
                recipeAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onRecipeItemClick(recipe: RecipeModel) {
        val recipeDetailsFragment = RecipeDetailsFragment(recipe)
        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        recipeDetailsFragment.arguments = bundle

        makeCurrentFragment(recipeDetailsFragment)
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fl_wrapper, fragment)
            addToBackStack(null)
            commit()
        }

}
