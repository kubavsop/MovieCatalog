package com.example.moviescatalog.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.ActivityMainBinding
import com.example.moviescatalog.presentation.feature_profile_screen.screen.ProfileFragment
import com.example.moviescatalog.presentation.feature_profile_screen.screen.ProfileFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.auth_selection.AuthSelectionFragment
import com.example.moviescatalog.presentation.feature_user_auth.auth_selection.AuthSelectionFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.password_registration.PasswordRegistrationFragment
import com.example.moviescatalog.presentation.feature_user_auth.user_login.UserLoginFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.password_registration.PasswordRegistrationFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.RegistrationDetailsFragment
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.RegistrationDetailsFragmentDirections
import com.example.moviescatalog.presentation.feature_user_auth.user_login.UserLoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserLoginFragment.FragmentCallBack,
    RegistrationDetailsFragment.FragmentCallBack, PasswordRegistrationFragment.FragmentCallBack,
    AuthSelectionFragment.FragmentCallBack, ProfileFragment.FragmentCallBack {
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> showBottomNav()
                R.id.favoriteFragment -> showBottomNav()
                R.id.profileFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

        setContentView(binding.root)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
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

    override fun openMainFromUserLogin() {
        val action = UserLoginFragmentDirections.actionUserLoginFragmentToMainFragment()
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

    override fun openMainFromPasswordRegistration() {
        val action =
            PasswordRegistrationFragmentDirections.actionPasswordRegistrationFragmentToMainFragment()
        navController.navigate(action)
    }

    override fun openAuthSelectionScreenFromProfile() {
        Unit
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


    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}