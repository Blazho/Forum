package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.helper_class.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission, Long>{

    fun findByTitleIgnoreCase(title: String): Permission?

}