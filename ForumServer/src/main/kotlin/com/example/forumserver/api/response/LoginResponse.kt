package com.example.forumserver.api.response

import com.example.forumserver.core.entity.enums.PermissionLayer

data class LoginResponse(
    val token: String,
    val userPermissions: Map<String, PermissionLayer>
)
