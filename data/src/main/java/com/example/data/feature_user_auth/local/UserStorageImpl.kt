package com.example.data.feature_user_auth.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class UserStorageImpl(context: Context) : UserStorage {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        SHARED_PREF_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(tokenResponseEntity: TokenResponseEntity) {
        sharedPreferences.edit().putString(TOKEN_KEY, tokenResponseEntity.token)
            .apply()
    }

    override fun getToken(): TokenResponseEntity? {
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        return token?.let { TokenResponseEntity(token = it) }
    }

    private companion object {
        const val SHARED_PREF_NAME = "shared_pref"
        const val TOKEN_KEY = "token"
    }
}