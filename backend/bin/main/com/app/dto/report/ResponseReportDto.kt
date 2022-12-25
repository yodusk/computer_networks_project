package com.app.dto.report

import com.app.dto.project.ResponseProjectDto
import com.app.dto.rule.result.ResponseRuleResultDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class ResponseReportDto(
    val id: Long?,
    val project: ResponseProjectDto,
    var checks: List<ResponseRuleResultDto>,
    var branchName: String,
    var reportTimestamp: LocalDateTime?,
    var updatedAt: LocalDateTime?
)
