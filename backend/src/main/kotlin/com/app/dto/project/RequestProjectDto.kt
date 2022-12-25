package com.app.dto.project

import com.app.entities.BuildSystem
import com.app.entities.Project
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
data class RequestProjectDto(
    var name: String,
    val url: String,
    val defaultBranch: String,
    val buildSystem: BuildSystem
) {
    fun toEntity(): Project {
        return Project(
            null,
            name,
            url,
            buildSystem,
            defaultBranch,
            listOf(),
            listOf(),
            null,
            null
        )
    }
}
