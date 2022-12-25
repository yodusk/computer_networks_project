package com.app.controllers

import com.app.dto.CheckProjectRequestDto
import com.app.dto.project.RequestProjectDto
import com.app.dto.project.ResponseProjectDto
import com.app.entities.CheckProjectRequest
import com.app.entities.toResponseDto
import com.app.services.ProjectService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject

@Controller("/project")
class ProjectController {

    @Inject lateinit var projectService: ProjectService

    @Post
    fun createProject(project: RequestProjectDto): ResponseProjectDto {
        return projectService.create(project)
    }

    @Post("/{id}")
    fun updateProject(@PathVariable id: Long, project: RequestProjectDto): ResponseProjectDto {
        return projectService.update(id, project)
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: Long) {
        return projectService.delete(id)
    }

    @Get
    fun getProjects(): List<ResponseProjectDto> {
        return projectService.getProjects()
    }

    @Get("/{id}")
    fun getProject(@PathVariable id: Long): ResponseProjectDto {
        return projectService.getById(id)
    }

    @Post("/check/{id}/{branchName}")
    fun checkProject(
        @PathVariable id: Long,
        @PathVariable branchName: String
    ): CheckProjectRequestDto {
        return projectService.createCheckRequest(id, branchName).toResponseDto()
    }

    @Get("/check/{id}")
    fun getChecks(@PathVariable id: Long): List<CheckProjectRequestDto> {
        return projectService.getChecks(id).map { it.toResponseDto() }
    }
}
