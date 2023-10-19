package com.example.moviescatalog.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController: NavController
        get() {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
            return navHostFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openUserAuthorization() {
        navController.navigate(R.id.userLoginFragment)
    }

    fun openDetailedUserRegistration() {
        navController.navigate(R.id.registrationDetailsFragment)
    }

    fun openPasswordRegistration() {
        navController.navigate(R.id.passwordRegistrationFragment)
    }
}