package com.example.mysyncapp.data.repository

import android.content.Context
import android.util.Log
import com.example.mysyncapp.data.AppDatabase
import com.example.mysyncapp.data.entity.SyncItem
import com.example.mysyncapp.network.RetrofitClient
import com.example.mysyncapp.network.SyncItemDto
import com.example.mysyncapp.network.SyncRequest

class SyncRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.syncItemDao()
    private val api = RetrofitClient.apiService

    suspend fun addLocalItem(item: SyncItem): Long {return dao.insert(item)}
    suspend fun getPending(): List<SyncItem> = dao.getPendingItems()
    suspend fun pushPendingToServer(): Boolean {
        val pending = dao.getPendingItems()
        if (pending.isEmpty()) return true
        val batchSize = 50
        val chunks = pending.chunked(batchSize)
        var allOk = true
        for (chunk in chunks) {
            val dtos = chunk.map { p ->
                SyncItemDto(
                    clientId = p.id,
                    title = p.title,
                    body = p.body,
                    timestamp = p.timestamp
                )
            }
            val request = SyncRequest(items = dtos)
            val response = api.syncItems(request)
            if (response.isSuccessful) {
                val ids = chunk.map { it.id }
                dao.markAsSynced(ids)
            } else {
                Log.e("SYNC", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                allOk = false
            }
        }//end for
        return allOk
    }
}
