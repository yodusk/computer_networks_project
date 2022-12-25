package com.app.dto.project

import com.app.entities.BuildSystem
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class ResponseProjectDto(
    val id: Long?,
    val name: String,
    val url: String,
    val defaultBranch: String,
    val paths: String,
    val buildSystem: BuildSystem,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
