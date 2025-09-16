package com.example.forumserver.core.entity.projection

import com.example.forumserver.core.entity.enums.PermissionLayer

interface UserPermissionProjection {
    val title: String
    val permissionLayer: PermissionLayer
}