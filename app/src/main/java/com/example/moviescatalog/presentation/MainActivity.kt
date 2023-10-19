package com.example.moviescatalog.presentation

import android.os.Bundle
import android.text.Layout.Directions
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.ActivityMainBinding
import com.example.moviescatalog.presentation.auth_selection.AuthSelectionFragmentDirections
import com.example.moviescatalog.presentation.password_registration.PasswordRegistrationFragmentDirections
import com.example.moviescatalog.presentation.registration_details.RegistrationDetailsFragmentDirections
import com.example.moviescatalog.presentation.user_login.UserLoginFragmentDirections
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


    fun openAuthSelectionFromLogin() {
        val action = UserLoginFragmentDirections.actionUserLoginFragmentToAuthSelectionFragment()
        navController.navigate(action)
    }

    fun openAuthSelectionFromRegistration() {
        val action =
            RegistrationDetailsFragmentDirections.actionRegistrationDetailsFragmentToAuthSelectionFragment()
        navController.navigate(action)
    }

    fun openUserAuthorization() {
        val action =
            AuthSelectionFragmentDirections.actionAuthSelectionFragmentToUserLoginFragment()
        navController.navigate(action)
    }

    fun openDetailedUserRegistrationFromSelection() {
        val action =
            AuthSelectionFragmentDirections.actionAuthSelectionFragmentToRegistrationDetailsFragment()
        navController.navigate(action)
    }

    fun openDetailedUserRegistrationFromPasswordRegistration() {
        val action =
            PasswordRegistrationFragmentDirections.actionPasswordRegistrationFragmentToRegistrationDetailsFragment()
        navController.navigate(action)
    }

    fun openPasswordRegistration(
        userName: String,
        name: String,
        email: String,
        birthDate: String,
        gender: String
    ) {
        val action =
            RegistrationDetailsFragmentDirections.actionRegistrationDetailsFragmentToPasswordRegistrationFragment(
                userName = userName,
                name = name,
                email = email,
                birthDate = birthDate,
                gender = gender
            )
        navController.navigate(action)
    }
}