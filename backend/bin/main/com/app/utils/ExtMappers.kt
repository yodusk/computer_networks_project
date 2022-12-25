package com.app.utils

import com.app.dto.project.ResponseProjectDto
import com.app.dto.report.ResponseReportDto
import com.app.dto.rule.CreateRuleDto
import com.app.dto.rule.ResponseRuleDto
import com.app.dto.rule.result.ResponseRuleResultDto
import com.app.entities.Project
import com.app.entities.ProjectReport
import com.app.entities.ProjectRule
import com.app.entities.RuleResult

fun ProjectReport.toResponseDto(): ResponseReportDto {
    return ResponseReportDto(
        this.id,
        this.project.toResponseDto(),
        this.checks.map { it.toResponseDto() },
        this.branchName,
        this.reportTimestamp,
        this.updatedAt)
}

fun RuleResult.toResponseDto(): ResponseRuleResultDto {
    return ResponseRuleResultDto(
        this.id, this.rule.toResponseDto(), this.passed, this.createdAt, this.updatedAt)
}

fun ProjectRule.toResponseDto(): ResponseRuleDto {
    return ResponseRuleDto(this.id, this.name, this.query, this.description, this.createdAt, this.updatedAt)
}

fun Project.toResponseDto(): ResponseProjectDto {
    return ResponseProjectDto(
        this.id,
        this.name,
        this.url,
        this.defaultBranch,
        this.buildSystem,
        this.createdAt,
        this.updatedAt)
}

fun CreateRuleDto.toEntity(project: Project): ProjectRule {
    return ProjectRule(null, this.name, this.query, this.description, listOf(), project, null, null)
}
