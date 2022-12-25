package com.app.services

import com.app.dto.rule.CreateRuleDto
import com.app.dto.rule.ResponseRuleDto
import com.app.entities.Project
import com.app.entities.ProjectRule
import com.app.entities.RuleResult
import com.app.repositories.ProjectRepository
import com.app.repositories.ProjectRuleRepository
import com.app.repositories.RuleResultRepository
import com.app.utils.toEntity
import com.app.utils.toResponseDto
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional
import org.neo4j.driver.Driver

@Singleton
open class ProjectRuleService {

    @Inject lateinit var driver: Driver

    @Inject lateinit var ruleRepo: ProjectRuleRepository

    @Inject lateinit var projectRepo: ProjectRepository

    @Inject lateinit var resultRepo: RuleResultRepository

    @Transactional
    open fun create(rule: CreateRuleDto): ResponseRuleDto {
        val project = getProject(rule.projectId)
        return rule.toEntity(project).let { ruleRepo.save(it) }.toResponseDto()
    }

    @Transactional
    open fun getProject(id: Long): Project {
        return projectRepo.findById(id).orElseThrow { throw IllegalArgumentException() }
    }

    @Transactional
    open fun update(id: Long, rule: CreateRuleDto): ResponseRuleDto {
        return with(getEntityById(id)) {
            if (project.id != rule.projectId) {
                project = getProject(rule.projectId)
            }
            name = rule.name
            query = rule.query
            ruleRepo.save(this).toResponseDto()
        }
    }

    @Transactional
    open fun getEntityById(id: Long): ProjectRule {
        return ruleRepo.findById(id).orElseThrow { throw IllegalArgumentException() }
    }

    @Transactional
    open fun getById(id: Long): ResponseRuleDto {
        return getEntityById(id).toResponseDto()
    }

    @Transactional
    open fun delete(id: Long) {
        ruleRepo.deleteById(id)
    }

    @Transactional
    open fun check(projectRule: ProjectRule): RuleResult {
        return resultRepo.save(RuleResult(rule = projectRule, passed = execute(projectRule)))
    }

    @Transactional
    open fun execute(projectRule: ProjectRule): Boolean {
        return with(driver.session()) {
            run(projectRule.query)
                .also { k ->
                    k.also(::println)
                }
                .list()
                .isEmpty()
        }
    }

    @Transactional
    open fun getAll(): List<ResponseRuleDto> {
        return ruleRepo.findAll().map(ProjectRule::toResponseDto)
    }

    @Transactional
    open fun getByProjectId(id: Long): List<ResponseRuleDto> {
        val proj = projectRepo.findById(id).orElseThrow { throw IllegalArgumentException() }
        return ruleRepo.findByProject(proj).map(ProjectRule::toResponseDto)
    }
}
