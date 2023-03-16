package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.Toast
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
    private lateinit var noResult: ImageView

    private lateinit var radioGroup: RadioGroup

    private var pageRecipe: Int = 1
    private val token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"

    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recipeRV = view.findViewById(R.id.idRVRecipe)
        searchSV = view.findViewById(R.id.SVrecipe)
        noResult = view.findViewById(R.id.no_results_icon)
        radioGroup = view.findViewById(R.id.category_radio_group)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pageRecipe = 1
        searchSV.setQuery("", false)
        searchSV.clearFocus()
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
                println("send text")
                pageRecipe = 1
                radioGroup.isSelected = false
                recipeArrayList.clear()
                searchRecipes(recipeArrayList, recipeAdapter)
                return false
            }
        })

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            searchSV.setQuery("", false)
            pageRecipe = 1
            val recipeArrayList: ArrayList<RecipeModel> = ArrayList()
            val recipeAdapter = RecipeAdapter(requireContext(), recipeArrayList, this)
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            searchRecipes(recipeArrayList, recipeAdapter)
            recipeRV.layoutManager = linearLayoutManager
            recipeRV.adapter = recipeAdapter
        }


        recipeRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recipeArrayList.count() >= 28 && linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.itemCount - 1 * pageRecipe) {
                    pageRecipe++
                    searchRecipes(recipeArrayList, recipeAdapter)
                } else
                    return
                super.onScrolled(recipeRV, dx, dy)
            }
        })
    }

    private fun searchRecipes(
        recipeArrayList: ArrayList<RecipeModel>,
        recipeAdapter: RecipeAdapter,
    ) {
        var category = radioGroup.checkedRadioButtonId.let {
            when (it) {
                R.id.allRecipes -> searchSV.query.toString()
                R.id.rbDessert -> "sugar cake"
                R.id.rbAperitifDinatoire -> "cocktail"
                R.id.rbBurger -> "burger"
                R.id.rbMexicain -> "mexican"
                R.id.rbJaponais -> "japanese"
                R.id.rbCuban -> "cuban"
                else -> ""
            }
        }

        val api = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)
        lifecycleScope.launch {
            try {
                val response = api.searchRecipes(
                    pageRecipe,
                    category,
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
                    if (recipeList != null && recipeList.isNotEmpty()) {
                        recipeRV.visibility = View.VISIBLE
                        noResult.visibility = View.GONE
                        recipeArrayList.addAll(recipeList)
                        recipeAdapter.notifyDataSetChanged()
                    } else {
                        recipeRV.visibility = View.GONE
                        noResult.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load recipes", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load recipes", Toast.LENGTH_SHORT)
                    .show()
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
