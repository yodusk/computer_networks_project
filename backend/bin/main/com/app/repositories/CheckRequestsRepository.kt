package com.app.repositories

import com.app.entities.CheckProjectRequest
import com.app.entities.CheckStatus
import com.app.entities.Project
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository
import org.hibernate.dialect.Dialect
import javax.transaction.Transactional

@Repository
interface CheckRequestsRepository : PageableRepository<CheckProjectRequest, Long> {

    @Transactional
    fun findByStatus(status: CheckStatus, pageable: Pageable): List<CheckProjectRequest>
    @Transactional
    fun findByProject(project: Project): List<CheckProjectRequest>
}
