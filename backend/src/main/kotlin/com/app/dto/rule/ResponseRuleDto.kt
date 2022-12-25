package com.app.dto.rule

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class ResponseRuleDto(
    val id: Long?,
    val name: String,
    val query: String,
    val description: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)
