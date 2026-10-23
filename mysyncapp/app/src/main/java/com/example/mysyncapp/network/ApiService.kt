package com.example.mysyncapp.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class SyncItemDto(
    val clientId: Long?,
    val title: String,
    val body: String,
    val timestamp: Long
)

data class SyncRequest(val items: List<SyncItemDto>)

interface ApiService {
    @POST("/api/sync/items")
    suspend fun syncItems(@Body request: SyncRequest): Response<Unit>
}
