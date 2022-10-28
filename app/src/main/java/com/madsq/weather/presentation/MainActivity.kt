package com.madsq.weather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madsq.weather.R
import com.madsq.weather.databinding.ActivityMainBinding
import com.madsq.weather.presentation.x_others.testRentals


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
            ?.let { navController ->
                navView.setupWithNavController(navController)
            }
    }
}