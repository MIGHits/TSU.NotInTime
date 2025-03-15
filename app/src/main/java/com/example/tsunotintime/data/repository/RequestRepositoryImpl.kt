package com.example.tsunotintime.data.repository

import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.data.remote.RequestService
import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.domain.repository.RequestRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File

class RequestRepositoryImpl(private val requestService: RequestService) : RequestRepository {
    override suspend fun getRequest(requestId: String): RequestModel? {
        val response = requestService.getConcreteRequest(requestId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun getRequestList(userId: String): RequestListModel? {
        val response = requestService.getRequests(userId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun createRequest(
        absenceDateFrom: String,
        absenceDateTo: String,
        description: String,
        files: List<String>
    ): String? {
        val absenceDateFromBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateFrom)
        val absenceDateToBody = RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateTo)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        val fileParts = files.map { filePath ->
            val file = File(filePath)
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }

        val response = requestService.createRequest(
            absenceDateFromBody,
            absenceDateToBody,
            descriptionBody,
            fileParts
        )

        return if (response.isSuccessful) {
            response.body()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun editRequest(
        requestId: String,
        status: RequestStatus,
        images: List<String>,
        description: String,
        absenceDateFrom: String,
        absenceDateTo: String,
        newImages: List<String>
    ): String? {
        val absenceDateFromBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateFrom)
        val absenceDateToBody = RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateTo)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status.name)
        val fileParts = images.map { filePath ->
            val file = File(filePath)
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }

        val newParts = images.map { newImage ->
            val file = File(newImage)
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("files", file.name, requestBody)
        }

        val response = requestService.editRequest(
            requestId = requestId,
            absenceDateFrom = absenceDateFromBody,
            absenceDateTo = absenceDateToBody,
            description = descriptionBody,
            status = statusBody,
            Images = fileParts,
            newImages = newParts
        )

        return if (response.isSuccessful) {
            response.body()
        } else {
            throw HttpException(response)
        }
    }
}
