package com.app.controllers

import com.app.dto.report.ResponseReportDto
import com.app.services.ProjectReportService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import jakarta.inject.Inject

@Controller("/report")
class ReportController {

  @Inject lateinit var reportService: ProjectReportService

  @Get("/{id}")
  fun getReport(@PathVariable id: Long): ResponseReportDto {
    return reportService.getById(id)
  }

  @Get("project/{id}")
  fun getReports(@PathVariable id: Long): List<ResponseReportDto> {
    return reportService.getByProjectId(id)
  }
}
