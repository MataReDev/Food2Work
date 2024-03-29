package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.FavoriteRecipeAdapter
import com.example.food2work.OnFavoriteRecipeItemClickListener
import com.example.food2work.R
import com.example.food2work.RecipeModel
import com.example.food2work.database.AppDatabase
import com.example.food2work.database.RecipeFavorisDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavorisFragment : Fragment(), OnFavoriteRecipeItemClickListener {
    private val recipeFavorisDao: RecipeFavorisDao by lazy {
        AppDatabase.getInstance(requireContext()).favoriDao()
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvNoFavorites: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoris, container, false)
        recyclerView = view.findViewById(R.id.idRVFavorite)
        tvNoFavorites = view.findViewById(R.id.tvNoFavorites)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeArrayList: ArrayList<RecipeModel> = ArrayList()
        val favorisAdapter = FavoriteRecipeAdapter(requireContext(), recipeArrayList, this, recipeFavorisDao, recyclerView, tvNoFavorites)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        searchRecipes(recipeArrayList, favorisAdapter)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = favorisAdapter
    }
    private fun searchRecipes(
        recipeArrayList: ArrayList<RecipeModel>,
        recipeAdapter: FavoriteRecipeAdapter,
    ) {
        lifecycleScope.launch {
            val recipeList = withContext(Dispatchers.IO) {
                recipeFavorisDao.getAll()
            }
            recipeArrayList.addAll(recipeList)
            requireActivity().runOnUiThread {
                recipeAdapter.notifyDataSetChanged()
                // Afficher ou masquer le TextView en fonction de la taille de la liste des favoris
                if (recipeArrayList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    tvNoFavorites.visibility = View.VISIBLE
                } else {
                    tvNoFavorites.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fl_wrapper, fragment)
            addToBackStack(null)
            commit()
        }
    override fun onFavoriteRecipeItemClick(recipe: RecipeModel) {
        val recipeDetailsFragment = RecipeDetailsFragment(recipe)
        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        recipeDetailsFragment.arguments = bundle
        makeCurrentFragment(recipeDetailsFragment)
    }
}