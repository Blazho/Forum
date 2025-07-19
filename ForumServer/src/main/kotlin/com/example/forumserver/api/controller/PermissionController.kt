package com.example.forumserver.api.controller

import com.example.forumserver.api.dto.PermissionDTO
import com.example.forumserver.api.mapper.PermissionMapper
import com.example.forumserver.api.response.ApiResponse
import com.example.forumserver.api.response.PageResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/permission")
class PermissionController(
    private val permissionMapper: PermissionMapper
) {

    @GetMapping("/{permissionId}")
    fun findPermission(@PathVariable permissionId: Long): ResponseEntity<ApiResponse<PermissionDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = permissionMapper.findPermission(permissionId)))
        } catch (ex: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @GetMapping("/list")
    fun listPermissions(@RequestParam pageSize: Int, @RequestParam pageNumber: Int): PageResponse<PermissionDTO>{
        return permissionMapper.listPermissions(pageSize, pageNumber)
    }

    @PostMapping("/create")
    fun createPermission(@RequestBody request: PermissionDTO): ResponseEntity<ApiResponse<PermissionDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = permissionMapper.createPermission(request)))
        } catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @PutMapping("/edit/{permissionId}")
    fun editPermission(@RequestBody request: PermissionDTO, @PathVariable permissionId: Long): ResponseEntity<ApiResponse<PermissionDTO>>{
        return try {
            ResponseEntity.ok(ApiResponse(data = permissionMapper.editPermission(request, permissionId)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }
}