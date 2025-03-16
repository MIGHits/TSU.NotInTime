package com.example.tsunotintime.data.repository

import android.content.Context
import android.net.Uri
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.R
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.data.models.SortType
import com.example.tsunotintime.data.remote.RequestService
import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.domain.repository.RequestRepository
import com.example.tsunotintime.utils.ErrorParser.parseErrorMessage
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

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
        val response = requestService.getRequests(userId, sortType = SortType.CreateDesc)
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
        files: List<Uri>
    ): String? {
        val absenceDateFromBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateFrom)
        val absenceDateToBody = RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateTo)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        val images = prepareImagesForUpload("files", instance, files)

        val response = requestService.createRequest(
            absenceDateFromBody,
            absenceDateToBody,
            descriptionBody,
            images
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
        val absenceDateToBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), absenceDateTo)
        val descriptionBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        val imagesBody = images.map { imagePath ->
            val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), imagePath)
            MultipartBody.Part.createFormData("Images", null, requestBody)
        }

        val newParts = prepareImagesForUpload("newImages", instance, newImages)

        val response = requestService.editRequest(
            requestId = requestId,
            absenceDateFrom = absenceDateFromBody,
            absenceDateTo = absenceDateToBody,
            description = descriptionBody,
            status = null,
            Images = imagesBody,
            newImages = newParts
        )

        if (!response.isSuccessful) {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    private fun prepareImagesForUpload(
        name: String,
        context: Context,
        imageUris: List<Uri>
    ): List<MultipartBody.Part> {
        return imageUris.mapNotNull { uri ->
            val file = uriToFile(context, uri)
            if (file != null) {
                val requestBody = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(name, file.name, requestBody)
            } else {
                null
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        return try {
            val contentResolver = context.contentResolver
            val tempFile = File.createTempFile("temp_image", ".jpeg", context.cacheDir)
            tempFile.outputStream().use { output ->
                contentResolver.openInputStream(uri)?.use { input ->
                    input.copyTo(output)
                } ?: throw IOException(context.getString(R.string.inputstream_fail_to_load))
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
