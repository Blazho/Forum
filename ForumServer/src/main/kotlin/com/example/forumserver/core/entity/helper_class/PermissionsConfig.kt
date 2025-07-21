package com.example.forumserver.core.entity.helper_class

import com.example.forumserver.core.entity.enums.PermissionLayer

data class PermissionsConfig(
    val permissions: Map<String, Map<String, PermissionLayer>> = emptyMap()
)
