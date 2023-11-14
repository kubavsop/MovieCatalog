package com.example.data.common.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.UUID

class UserStorageImpl(context: Context) : UserStorage {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build();

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SHARED_PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    );

    override fun saveToken(tokenResponseEntity: TokenResponseEntity) {
        sharedPreferences.edit().putString(TOKEN_KEY, tokenResponseEntity.token)
            .apply()
    }

    override fun getToken(): TokenResponseEntity? {
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        return token?.let { TokenResponseEntity(token = it) }
    }

    override fun saveUser(user: UserEntity) {
        sharedPreferences.edit().putString(USER_ID_KEY, user.id)
            .apply()
    }

    override fun getUser(): UserEntity? {
        val userId = sharedPreferences.getString(USER_ID_KEY, null)
        return userId?.let { UserEntity(id = userId) }
    }


    private companion object {
        const val SHARED_PREF_NAME = "shared_pref"
        const val TOKEN_KEY = "token"
        const val USER_ID_KEY = "id"
    }
}