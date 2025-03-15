package com.example.tsunotintime.domain.repository

import android.net.Uri
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.UserModel
import retrofit2.Response

interface RequestRepository {
    suspend fun getRequest(requestId: String): RequestModel?
    suspend fun getRequestList(userId: String): RequestListModel?
    suspend fun createRequest(
        absenceDateFrom: String,
        absenceDateTo: String,
        description: String,
        files: List<String>
    ): String?

    suspend fun editRequest(
        requestId: String,
        status: RequestStatus,
        images: List<String>,
        description: String,
        absenceDateFrom: String,
        absenceDateTo: String,
        newImages: List<Uri>
    )
}