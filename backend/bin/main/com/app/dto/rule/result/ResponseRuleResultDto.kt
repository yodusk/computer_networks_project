package com.app.dto.rule.result

import com.app.dto.rule.ResponseRuleDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class ResponseRuleResultDto(
    val id: Long?,
    val rule: ResponseRuleDto,
    var passed: Boolean,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)
