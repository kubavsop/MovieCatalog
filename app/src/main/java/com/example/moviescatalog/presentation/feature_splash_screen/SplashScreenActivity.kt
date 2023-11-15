package com.example.moviescatalog.presentation.feature_splash_screen


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.moviescatalog.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var getProfileUseCase: GetProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            val isTokenAlive = try {
                getProfileUseCase()
                true
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                false
            }

            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.putExtra(IS_TOKEN_ALIVE, isTokenAlive)

            startActivity(intent)
            finish()
        }
    }

    private companion object {
        const val IS_TOKEN_ALIVE = "token"
    }
}