package com.example.backend.controller

import com.example.backend.service.SyncService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

data class SyncItemDto(val clientId: Long?, val title: String, val body: String, val timestamp: Long)
data class SyncRequest(val items: List<SyncItemDto>)

@RestController
@RequestMapping("/api/sync")
class SyncController(@Autowired val service: SyncService) {

    @PostMapping("/items")
    fun syncItems(@RequestBody req: SyncRequest): ResponseEntity<Map<String, String>> {
        service.saveItems(req.items)
        return ResponseEntity.ok(mapOf("status" to "OK", "count" to req.items.size.toString()))
    }
}
