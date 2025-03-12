package com.example.tsunotintime.data.repository

import com.example.tsunotintime.data.models.UserEditModel
import com.example.tsunotintime.data.remote.UserService
import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.UserModel
import com.example.tsunotintime.domain.repository.ProfileRepository
import retrofit2.HttpException

class ProfileRepositoryImpl(private val userService: UserService) : ProfileRepository {
    override suspend fun getProfile(): UserModel? {
        val response = userService.getProfile()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun updatePassword(userEditModel: String): ResponseModel? {
        val response = userService.updatePassword(UserEditModel(userEditModel))
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }
}