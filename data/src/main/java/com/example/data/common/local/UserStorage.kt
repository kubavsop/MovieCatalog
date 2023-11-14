package com.example.data.common.local

interface UserStorage {
    fun saveToken(tokenResponseEntity: TokenResponseEntity)
    fun getToken(): TokenResponseEntity?

    fun saveUser(user: UserEntity)
    fun getUser(): UserEntity?
}