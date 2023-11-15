package com.example.moviescatalog.presentation.feature_splas_screen


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LOGGER
import com.example.domain.common.usecase.GetTokenUseCase
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

//    @Inject
//    lateinit var getTokenUseCase: GetTokenUseCase

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

//        lifecycleScope.launch {
//            val hasToken = getTokenUseCase() != null
//
//            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
//            intent.putExtra(HAS_TOKEN, hasToken)
//
//            startActivity(intent)
//            finish()
//        }

//        Handler(Looper.getMainLooper()).post {
//            val hasToken = getTokenUseCase() != null
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra(HAS_TOKEN, hasToken)
//
//            startActivity(intent)
//            finish()}