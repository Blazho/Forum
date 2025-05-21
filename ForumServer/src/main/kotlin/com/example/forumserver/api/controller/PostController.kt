package com.example.forumserver.api.controller

import com.example.forumserver.api.dto.PostDTO
import com.example.forumserver.api.mapper.PostMapper
import com.example.forumserver.api.request.PostRequest
import com.example.forumserver.api.response.PageResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController(
    private val postMapper: PostMapper
) {

    //todo make it get
    @PostMapping
    fun getPosts(@RequestBody postRequest: PostRequest): PageResponse<PostDTO> {
        return postMapper.findPosts(postRequest)
    }
}