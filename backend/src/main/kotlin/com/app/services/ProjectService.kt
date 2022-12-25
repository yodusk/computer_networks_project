package com.app.services

import com.app.dto.project.RequestProjectDto
import com.app.dto.project.ResponseProjectDto
import com.app.dto.report.ResponseReportDto
import com.app.entities.CheckProjectRequest
import com.app.entities.Project
import com.app.entities.ProjectReport
import com.app.repositories.CheckRequestsRepository
import com.app.repositories.ProjectReportRepository
import com.app.repositories.ProjectRepository
import com.app.utils.ProjectUtils.Companion.getProjectName
import com.app.utils.toResponseDto
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ProjectService {

    @Inject lateinit var assistantService: JQAssistantService

    @Inject lateinit var builderService: ProjectBuilderService

    @Inject lateinit var projectRepo: ProjectRepository

    @Inject lateinit var reportRepo: ProjectReportRepository

    @Inject lateinit var ruleService: ProjectRuleService

    @Inject lateinit var checkRequestsRepository: CheckRequestsRepository

    @Transactional
    open fun createCheckRequest(id: Long, branchName: String): CheckProjectRequest {
        return with(getEntityById(id)) {
            checkRequestsRepository.save(CheckProjectRequest(project = this, branch = branchName))
        }
    }

    @Transactional
    open fun getChecks(id: Long): List<CheckProjectRequest> {
        return with(getEntityById(id)) {
            checkRequestsRepository.findByProject(this)
        }
    }

    @Transactional
    open fun runProjectCheck(id: Long, branchName: String): ResponseReportDto {
        return with(getEntityById(id)) {
            builderService.initialiseProject(this)
            builderService.syncProject(this, branchName)
            builderService.buildProject(this)
            assistantService.prepareProjectForRules(this)
            reportRepo
                .save(
                    ProjectReport(
                        project = this,
                        branchName = branchName,
                        checks = this.rules.map(ruleService::check)))
                .toResponseDto()
        }
    }

    @Transactional
    open fun create(projectDto: RequestProjectDto): ResponseProjectDto {
        return with(projectDto) {
            name = getProjectName(name)
            projectRepo.save(this.toEntity()).toResponseDto()
        }
    }

    @Transactional
    open fun update(id: Long, project: RequestProjectDto): ResponseProjectDto {
        return with(getEntityById(id)) {
            name = getProjectName(project.name)
            url = project.url
            buildSystem = project.buildSystem
            defaultBranch = project.defaultBranch
            projectRepo.save(this).toResponseDto()
        }
    }

    @Transactional
    open fun getEntityById(id: Long): Project {
        return projectRepo.findById(id).orElseThrow { throw IllegalArgumentException() }
    }

    @Transactional
    open fun getById(id: Long): ResponseProjectDto {
        return getEntityById(id).toResponseDto()
    }

    @Transactional
    open fun delete(id: Long) {
        return with(getEntityById(id)) {
            builderService.projectCleanUp(this)
            projectRepo.deleteById(this.id!!)
        }
    }

    @Transactional
    open fun exists(project: RequestProjectDto): Boolean {
        return projectRepo.existsByUrl(project.url)
    }

    @Transactional
    open fun getProjects(): List<ResponseProjectDto> {
        return projectRepo.findAll().map(Project::toResponseDto)
    }
}
