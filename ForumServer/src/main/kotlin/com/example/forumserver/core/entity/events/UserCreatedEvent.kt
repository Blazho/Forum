package com.example.forumserver.core.entity.events

import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.tables.Permission
import com.example.forumserver.core.entity.tables.User
import org.springframework.context.ApplicationEvent

class UserCreatedEvent(
    source: Any,
    val user: User,
    val permissions: Map<Permission, PermissionLayer>
): ApplicationEvent(source)
