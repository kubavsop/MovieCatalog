package com.example.moviescatalog.presentation


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.common.usecase.GetTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var getTokenUseCase: GetTokenUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).post {
            val hasToken = getTokenUseCase() != null

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(HAS_TOKEN, hasToken)

            startActivity(intent)
            finish()
        }
    }

    private companion object {
        const val HAS_TOKEN = "token"
    }
}