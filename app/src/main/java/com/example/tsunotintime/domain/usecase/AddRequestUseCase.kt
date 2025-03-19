package com.example.tsunotintime.domain.usecase

import android.net.Uri
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.repository.RequestRepository

class AddRequestUseCase(
    private val repository: RequestRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(
        absenceDateFrom: String,
        absenceDateTo: String,
        description: String,
        images: List<Uri>
    ): Result<String?> {
        return try {
            val response =
                repository.createRequest(absenceDateFrom, absenceDateTo, description, images)
            Result.Success(response)
        } catch (throwable: Throwable) {
            return Result.Error(errorHandler.getError(throwable))
        }
    }
}