package com.example.tsunotintime.data.repository

import android.content.Context
import android.net.Uri
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.data.remote.RequestService
import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.domain.repository.RequestRepository
import com.example.tsunotintime.utils.ErrorParser.parseErrorMessage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
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
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    override suspend fun editRequest(
        requestId: String,
        status: RequestStatus,
        images: List<String>,
        description: String,
        absenceDateFrom: String,
        absenceDateTo: String,
        newImages: List<Uri>
    ) {
        val absenceDateFromBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateFrom)
        val absenceDateToBody = RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateTo)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val fileParts = images.map { filePath ->
            val file = File(filePath)
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("Images", file.name, requestBody)
        }

        val newParts = prepareImagesForUpload(instance, newImages)

        val response = requestService.editRequest(
            requestId = requestId,
            absenceDateFrom = absenceDateFromBody,
            absenceDateTo = absenceDateToBody,
            description = descriptionBody,
            status = null,
            Images = fileParts,
            newImages = newParts
        )
        if (!response.isSuccessful) {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }
}

fun prepareImagesForUpload(context: Context, imageUris: List<Uri>): List<MultipartBody.Part> {
    return imageUris.map { uri ->
        val file = uriToFile(context, uri)
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("newImages", file.name, requestBody)
    }
}

fun uriToFile(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver
    val tempFile = File.createTempFile("temp_image", ".jpeg", context.cacheDir)
    tempFile.outputStream().use { output ->
        contentResolver.openInputStream(uri)?.use { input ->
            input.copyTo(output)
        }
    }
    return tempFile
}