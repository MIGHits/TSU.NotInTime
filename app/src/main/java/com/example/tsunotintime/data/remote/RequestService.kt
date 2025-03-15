package com.example.tsunotintime.data.remote

import com.example.tsunotintime.common.URL.GET_CONCRETE_REQUEST
import com.example.tsunotintime.common.URL.GET_TARGET_USER_REQUESTS
import com.example.tsunotintime.common.URL.REQUEST
import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.data.models.SortType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestService {
    @GET(GET_CONCRETE_REQUEST)
    suspend fun getConcreteRequest(@Path("id") requestId: String): Response<RequestModel>

    @GET(GET_TARGET_USER_REQUESTS)
    suspend fun getRequests(
        @Path("userId") userId: String,
        @Query("sortType") sortType: SortType? = null,
        @Query("requestStatus") requestStatus: RequestStatus? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null
    ): Response<RequestListModel>

    @Multipart
    @POST(REQUEST)
    suspend fun createRequest(
        @Part("AbsenceDateFrom") absenceDateFrom: RequestBody,
        @Part("AbsenceDateTo") absenceDateTo: RequestBody,
        @Part("Description") description: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Response<String>

    @Multipart
    @PUT(GET_CONCRETE_REQUEST)
    suspend fun editRequest(
        @Path("id") requestId: String,
        @Part("Status") status: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("AbsenceDateFrom") absenceDateFrom: RequestBody,
        @Part("AbsenceDateTo") absenceDateTo: RequestBody,
        @Part Images: List<MultipartBody.Part>,
        @Part newImages: List<MultipartBody.Part>
    ): Response<String>
}