package com.app.repositories

import com.app.entities.ProjectReport
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository interface ProjectReportRepository : CrudRepository<ProjectReport, Long>
