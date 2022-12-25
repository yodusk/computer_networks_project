package com.app.dto.rule

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable


@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class CreateRuleDto(
    val id: Long,
    val name: String,
    val query: String,
    val description: String,
    val projectId: Long
)
