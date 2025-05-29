package com.example.forumserver.api.controller

import com.example.forumserver.api.dto.ThreadDTO
import com.example.forumserver.api.mapper.ThreadMapper
import com.example.forumserver.api.response.ApiResponse
import com.example.forumserver.api.response.PageResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
                          @RequestParam pageSize: Int): ResponseEntity<PageResponse<ThreadDTO>> {
        return ResponseEntity.ok( threadMapper.findThreads(pageNum, pageSize) )
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
}