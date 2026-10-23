package com.example.backend.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.example.backend.controller.SyncItemDto

@Service
class SyncService(val jdbc: JdbcTemplate) {
    @Transactional
    fun saveItems(items: List<SyncItemDto>) {
        // Example: insert using ON CONFLICT DO NOTHING on a unique constraint on (client_id)
        // Make sure you created a unique index on client_id in the SQL schema if you want this behavior.
        val sql = """
            INSERT INTO sync_items (client_id, title, body, timestamp)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (client_id) DO NOTHING
        """.trimIndent()

        for (it in items) {
            jdbc.update(sql, it.clientId, it.title, it.body, it.timestamp)
        }
    }
}
