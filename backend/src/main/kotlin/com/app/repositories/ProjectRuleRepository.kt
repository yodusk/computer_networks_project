package com.app.repositories

import com.app.entities.Project
import com.app.entities.ProjectRule
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.transaction.Transactional

@Repository interface ProjectRuleRepository : CrudRepository<ProjectRule, Long> {
    @Transactional
    fun findByProject(project: Project): List<ProjectRule>
}
