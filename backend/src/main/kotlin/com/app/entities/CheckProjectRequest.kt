package com.app.entities

import com.app.dto.CheckProjectRequestDto
import com.app.dto.project.ResponseProjectDto
import com.app.dto.report.ResponseReportDto
import com.app.utils.toResponseDto
import io.micronaut.serde.annotation.Serdeable
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "check_requests")
@Serdeable.Deserializable
@Serdeable.Serializable
class CheckProjectRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne
    var project: Project,

    @Column(name = "branch", nullable = false)
    var branch: String,

    @Column(name = "status", nullable = false)
    var status: CheckStatus = CheckStatus.ENQUEUED,

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

)

fun CheckProjectRequest.toResponseDto(): CheckProjectRequestDto {
    return CheckProjectRequestDto(
        id = this.id!!,
        project = this.project.toResponseDto(),
        status = this.status,
        branch = this.branch,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt!!
    )
}