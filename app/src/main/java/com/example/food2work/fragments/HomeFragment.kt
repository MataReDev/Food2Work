package com.example.food2work.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.R
import com.example.food2work.RecipeAdapter
import com.example.food2work.RecipeModel

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

        //region Add items To array
        recipeArrayList.add(RecipeModel(
            "Pâtes à la Carbonara",
            "Un plat de pâtes crémeux et savoureux, facile à préparer en quelques minutes.",
            "https://www.finedininglovers.fr/sites/g/files/xknfdk1291/files/2021-04/pates%20carbonara%20iStock.jpg",
            4
        ))
        recipeArrayList.add(RecipeModel(
            "Salade César",
            "Une salade fraîche et croquante, parfaite pour une soirée d'été.",
            "https://recette.supertoinette.com/151436/b/salade-cesar.jpg",
            6
        ))
        recipeArrayList.add(RecipeModel(
            "Poulet rôti",
            "Un classique de la cuisine française, facile à préparer pour un repas familial.",
            "https://img.cuisineaz.com/1024x576/2016/10/23/i113627-poulet-roti-au-four.webp",
            5
        ))
        recipeArrayList.add(RecipeModel(
            "Pizza Margherita",
            "Une pizza classique et savoureuse avec de la sauce tomate, de la mozzarella et du basilic.",
            "https://img.mesrecettesfaciles.fr/wp-content/uploads/2016/09/pizzamargarita-1000x500.jpg",
            3
        ))
        recipeArrayList.add(RecipeModel(
            "Sushi au saumon",
            "Des rouleaux de sushi délicieux et sains remplis de saumon frais et de légumes croquants.",
            "https://adc-dev-images-recipes.s3.eu-west-1.amazonaws.com/REP_fk_12447_sushi_saumon_entree_norge_asie.JPG",
            4
        ))
        recipeArrayList.add(RecipeModel(
            "Ragoût de boeuf",
            "Un ragoût copieux et réconfortant rempli de légumes et de morceaux de boeuf tendres.",
            "https://img.cuisineaz.com/660x660/2013/12/20/i45409-ragout-de-boeuf-aux-carottes-et-puree-aux-herbes.jpeg",
            5
        ))
        recipeArrayList.add(RecipeModel(
            "Poulet Korma",
            "Un plat de poulet crémeux et épicé avec du riz basmati et des naans frais.",
            "https://assets.afcdn.com/recipe/20130823/5295_w1024h768c1cx2464cy1632.webp",
            4
        ))
        recipeArrayList.add(RecipeModel(
            "Tarte aux pommes",
            "Une tarte aux pommes classique et délicieuse avec une croûte croustillante et une garniture sucrée.",
            "https://static.750g.com/images/1200-630/9823eb627203c878f3e36d72f8ce6d1c/tarte-aux-pommes.jpg",
            3
        ))
        //endregion

        val courseAdapter = RecipeAdapter(requireContext(), recipeArrayList)
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recipeRV.layoutManager = linearLayoutManager
        recipeRV.adapter = courseAdapter
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
