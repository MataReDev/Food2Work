package com.example.food2work.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.food2work.R
import com.example.food2work.RecipeApiService
import com.example.food2work.RecipeModel
import com.example.food2work.database.AppDatabase
import com.example.food2work.fragments.FavorisFragment
import com.example.food2work.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeFavorisDao = AppDatabase.getInstance(this).favoriDao()
        val token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val listFavoris: List<RecipeModel> = recipeFavorisDao.getAll()
                for (favori in listFavoris) {
                    val api = Retrofit.Builder()
                        .baseUrl("https://food2fork.ca/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(RecipeApiService::class.java)
                    val response = api.searchRecipeById(id = favori.pk, token = token)
                    if (response != null) {
                        recipeFavorisDao.insert(response)
                        println(recipeFavorisDao.getRecipeCount())
                    }
                }
            }
        }
        val homeFragment = HomeFragment(recipeFavorisDao)
        val favorisFragment = FavorisFragment()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        makeCurrentFragment(homeFragment)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favoris -> makeCurrentFragment(favorisFragment)
            }
            true
        }
    }

    fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}