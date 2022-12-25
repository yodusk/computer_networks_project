package com.app.dto

import com.app.dto.project.ResponseProjectDto
import com.app.entities.CheckStatus
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class CheckProjectRequestDto(
    val id: Long? = null,
    val project: ResponseProjectDto,
    val branch: String,
    val status: CheckStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
