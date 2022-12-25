package com.app.services

import com.app.dto.report.ResponseReportDto
import com.app.entities.ProjectReport
import com.app.repositories.ProjectReportRepository
import com.app.utils.toResponseDto
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ProjectReportService {

    @Inject lateinit var reportRepository: ProjectReportRepository

    @Inject lateinit var projectService: ProjectService

    @Transactional
    open fun getById(id: Long): ResponseReportDto {
        return reportRepository.findById(id).map(ProjectReport::toResponseDto).orElseThrow {
            throw IllegalArgumentException()
        }
    }

    @Transactional
    open fun getByProjectId(id: Long): List<ResponseReportDto> {
        return projectService.getEntityById(id).reports.map(ProjectReport::toResponseDto)
    }
}
