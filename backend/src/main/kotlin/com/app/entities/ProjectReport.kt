package com.app.entities

import com.app.dto.project.ResponseProjectDto
import com.app.dto.report.ResponseReportDto
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
@Table(name = "reports")
@Serdeable.Deserializable
@Serdeable.Serializable
class ProjectReport(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne
    var project: Project,

    @OneToMany(mappedBy = "report")
    var checks: List<RuleResult>,

    @Column(name = "branch_name", nullable = false)
    var branchName: String,

    @CreationTimestamp
    @Column(name = "report_timestamp")
    var reportTimestamp: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

)
