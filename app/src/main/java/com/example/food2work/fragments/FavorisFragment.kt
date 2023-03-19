package com.example.food2work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food2work.FavoriteRecipeAdapter
import com.example.food2work.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavorisFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoris, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.idRVFavorite)
        val adapter = FavoriteRecipeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val favoriDao = AppDatabase.getInstance(requireContext())?.favoriDao()

        favoriDao?.getAll()?.observe(viewLifecycleOwner) { favoris ->
            if (favoris.isNotEmpty()) {
                adapter.setData(favoris)
            }
        }

        return view
    }
}