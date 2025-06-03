package com.example.forumserver.api.controller

import com.example.forumserver.api.dto.ThreadDTO
import com.example.forumserver.api.mapper.ThreadMapper
import com.example.forumserver.api.response.ApiResponse
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.core.entity.projection.ThreadEntityPairProjection
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/thread")
class ThreadController(
    private val threadMapper: ThreadMapper
) {

    @GetMapping("/{threadId}")
    fun getThread(@PathVariable threadId: Long): ResponseEntity<ApiResponse<ThreadDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = threadMapper.findThread(threadId)))
        } catch (ex: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @GetMapping("/list")
    fun listParentThreads(@RequestParam pageNum: Int,
                          @RequestParam pageSize: Int): ResponseEntity<ApiResponse<PageResponse<ThreadDTO>>> {
        return ResponseEntity.ok(ApiResponse(data = threadMapper.findThreads(pageNum, pageSize)) )
    }

    @GetMapping("/list/{threadId}")
    fun findChildThreads(@PathVariable threadId: Long,
                               @RequestParam pageNum: Int,
                               @RequestParam pageSize: Int): ResponseEntity<ApiResponse<PageResponse<ThreadDTO>>> {
        return try {
            val pageResult = threadMapper.findChildThreads(threadId = threadId,
                pageNum = pageNum,
                pageSize = pageSize)
            ResponseEntity.ok(ApiResponse(data = pageResult))
        } catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @PostMapping("/create")
    fun createThread(@RequestBody threadDTO: ThreadDTO): ResponseEntity<ApiResponse<ThreadDTO>> {
        return try {
            ResponseEntity.ok(ApiResponse(data = threadMapper.createThread(threadDTO)))
        } catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @GetMapping("/parentable")
    fun listParentableThreads(): ResponseEntity<ApiResponse<List<ThreadEntityPairProjection>>> {
        return ResponseEntity.ok(ApiResponse(data = threadMapper.listParentableThreads()))
    }
}