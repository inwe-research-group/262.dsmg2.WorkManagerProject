package com.example.mysyncapp.data.dao

import androidx.room.*
import com.example.mysyncapp.data.entity.SyncItem

@Dao
interface SyncItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SyncItem): Long

    @Query("SELECT * FROM sync_items WHERE synced = 0 ORDER BY timestamp ASC")
    suspend fun getPendingItems(): List<SyncItem>

    @Query("UPDATE sync_items SET synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)

    @Query("DELETE FROM sync_items WHERE synced = 1")
    suspend fun deleteSynced()
}