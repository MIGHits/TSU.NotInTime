package com.example.tsunotintime.domain.repository

import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.UserModel

interface ProfileRepository {
    suspend fun getProfile(): UserModel?
    suspend fun updatePassword(userEditModel: String): ResponseModel?
}