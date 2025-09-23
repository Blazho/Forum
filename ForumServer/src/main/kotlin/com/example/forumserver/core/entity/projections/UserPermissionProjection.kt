package com.example.forumserver.core.entity.projections

import com.example.forumserver.core.entity.enums.PermissionLayer

interface UserPermissionProjection {
    val title: String
    val permissionLayer: PermissionLayer
}