package com.example.forumserver.api.response


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PageResponse <Content> (
    val content: List<Content>,
    val totalElements: Long,
    val pageable: Pageable
)

fun <E, Content> Page<E>.toPageResponse(mapFunction: (E) -> Content): PageResponse<Content> {
    return PageResponse(
        content = this.content.map(mapFunction),
        totalElements = this.totalElements,
        pageable = this.pageable
    )
}


