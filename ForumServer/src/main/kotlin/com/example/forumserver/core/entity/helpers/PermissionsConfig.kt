package com.example.forumserver.core.entity.helpers

import com.example.forumserver.core.entity.enums.PermissionLayer

data class PermissionsConfig(
    val permissions: Map<String, Map<String, PermissionLayer>> = emptyMap()
)
