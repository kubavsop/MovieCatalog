package com.example.moviescatalog.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.ActivityMainBinding
import com.example.moviescatalog.presentation.feature_user_auth.auth_selection.AuthSelectionFragment
import com.example.moviescatalog.presentation.feature_user_auth.auth_selection.AuthSelectionFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.password_registration.PasswordRegistrationFragment
import com.example.moviescatalog.presentation.feature_user_auth.user_login.UserLoginFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.password_registration.PasswordRegistrationFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.RegistrationDetailsFragment
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.RegistrationDetailsFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.user_login.UserLoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserLoginFragment.FragmentCallBack,
    RegistrationDetailsFragment.FragmentCallBack, PasswordRegistrationFragment.FragmentCallBack,
    AuthSelectionFragment.FragmentCallBack {
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


    override fun openAuthSelectionFromLogin() {
        val action = UserLoginFragmentDirections.actionUserLoginFragmentToAuthSelectionFragment()
        navController.navigate(action)
    }

    override fun openRegistrationDetailsFromUserLogin() {
        val action =
            UserLoginFragmentDirections.actionUserLoginFragmentToRegistrationDetailsFragment()
        navController.navigate(action)
    }

    override fun openUserLoginFromRegistrationDetails() {
        val action =
            RegistrationDetailsFragmentDirections.actionRegistrationDetailsFragmentToUserLoginFragment()
        navController.navigate(action)
    }

    override fun openAuthSelectionFromRegistration() {
        val action =
            RegistrationDetailsFragmentDirections.actionRegistrationDetailsFragmentToAuthSelectionFragment()
        navController.navigate(action)
    }

    override fun openUserAuthorization() {
        val action =
            AuthSelectionFragmentDirections.actionAuthSelectionFragmentToUserLoginFragment()
        navController.navigate(action)
    }

    override fun openDetailedUserRegistrationFromSelection() {
        val action =
            AuthSelectionFragmentDirections.actionAuthSelectionFragmentToRegistrationDetailsFragment()
        navController.navigate(action)
    }

    override fun openUserLoginFromPasswordRegistration() {
        val action =
            PasswordRegistrationFragmentDirections.actionPasswordRegistrationFragmentToUserLoginFragment()
        navController.navigate(action)
    }

    override fun openDetailedUserRegistrationFromPasswordRegistration() {
        val action =
            PasswordRegistrationFragmentDirections.actionPasswordRegistrationFragmentToRegistrationDetailsFragment()
        navController.navigate(action)
    }

    override fun openPasswordRegistration(
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