package com.example.forumserver.api.controller

import com.example.forumserver.api.dto.PostDTO
import com.example.forumserver.api.mapper.PostMapper
import com.example.forumserver.api.request.PostRequest
import com.example.forumserver.api.response.ApiResponse
import com.example.forumserver.api.response.PageResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController(
    private val postMapper: PostMapper
) {
    @GetMapping("{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<ApiResponse<PostDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = postMapper.getPost(postId)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    //todo make it get
    @PostMapping
    fun getPosts(@RequestBody postRequest: PostRequest): PageResponse<PostDTO> {
        return postMapper.findPosts(postRequest)
    }

    @PostMapping("/create")
    fun createPost(@RequestBody request: PostDTO): ResponseEntity<ApiResponse<PostDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = postMapper.createPost(request)))
        } catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @PutMapping("/edit/{postId}")
    fun editPost(@RequestBody postBody: PostDTO, @PathVariable postId: Long): ResponseEntity<ApiResponse<PostDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = postMapper.editPost(postBody, postId)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @DeleteMapping("/delete/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<ApiResponse<String>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = postMapper.deletePost(postId)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }
}