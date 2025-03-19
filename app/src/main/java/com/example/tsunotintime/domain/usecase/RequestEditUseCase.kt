package com.example.tsunotintime.domain.usecase

import android.net.Uri
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.repository.RequestRepository

class RequestEditUseCase(
    private val repository: RequestRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(
        requestId: String,
        status: RequestStatus,
        images: List<String>,
        description: String,
        absenceDateFrom: String,
        absenceDateTo: String,
        newImages: List<Uri>
    ): Result<Unit> {
        return try {
            val response = repository.editRequest(
                requestId,
                status,
                images,
                description,
                absenceDateFrom,
                absenceDateTo,
                newImages
            )
            Result.Success(response)
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}