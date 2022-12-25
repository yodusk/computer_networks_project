package com.app.services

import com.app.dto.report.ResponseReportDto
import com.app.entities.CheckProjectRequest
import com.app.entities.CheckStatus.ENQUEUED
import com.app.entities.CheckStatus.PROCESSED
import com.app.entities.CheckStatus.PROCESSING
import com.app.repositories.CheckRequestsRepository
import io.micronaut.data.model.Pageable
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class CheckerService {

    @Inject lateinit var projectService: ProjectService

    @Inject lateinit var requestsRepository: CheckRequestsRepository

    @Transactional
    @Scheduled(fixedDelay = "5s")
    open fun processCheckRequests() {
        val requests: List<CheckProjectRequest> =
            this.requestsRepository.findByStatus(ENQUEUED, Pageable.from(0, 10))
        if (requests.isNotEmpty()) {
            println("Processing check requests")
            println(requests.size)
        }
        requests.forEach { request -> println("Processing request: $request") }
        requests.map { it.status = PROCESSING }
        requestsRepository.updateAll(requests)
        requests.forEach(::process)
        requests.map { it.status = PROCESSED }
        requestsRepository.updateAll(requests)
    }

    open fun process(request: CheckProjectRequest): ResponseReportDto {
        return projectService.runProjectCheck(request.project.id!!, request.branch)
    }
}
