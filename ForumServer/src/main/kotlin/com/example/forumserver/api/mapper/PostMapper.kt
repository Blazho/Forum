package com.example.forumserver.api.mapper

import com.example.forumserver.api.dto.PostDTO
import com.example.forumserver.api.dto.toDTO
import com.example.forumserver.api.request.PostRequest
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.api.response.toPageResponse
import com.example.forumserver.core.service.PostService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostMapper(
    private val postService: PostService
) {
    fun findPosts(postRequest: PostRequest) : PageResponse<PostDTO> {
        //check permission

        //Find thread

        val pageable: Pageable = PageRequest
            .of(postRequest.pageNumber, postRequest.pageSize, Sort.by("dateCreated").ascending())
        //todo Optimise: make custom query for pagination
        return postService.findPosts(postRequest.threadId, pageable).toPageResponse { it.toDTO() }
    }

    fun createPost(request: PostDTO): PostDTO? {
        //permission

        return postService.createPost(request).toDTO()
    }


}