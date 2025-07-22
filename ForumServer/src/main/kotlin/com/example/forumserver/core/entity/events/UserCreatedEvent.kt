package com.example.forumserver.core.entity.events

import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.User
import org.springframework.context.ApplicationEvent

class UserCreatedEvent(
    source: Any,
    val user: User,
    val permissions: Map<Permission, PermissionLayer>
): ApplicationEvent(source)
