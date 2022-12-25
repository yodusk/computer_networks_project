package com.app.entities

import io.micronaut.serde.annotation.Serdeable
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "projects")
@Serdeable.Deserializable
@Serdeable.Serializable
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "project_name", nullable = false, unique = true)
    var name: String,

    @Column(name = "project_url", nullable = false)
    var url: String,

    @Column(name = "build_system")
    var buildSystem: BuildSystem,

    @Column(name = "default_branch")
    var defaultBranch: String,

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    var rules: List<ProjectRule> = listOf(),

    @OneToMany(mappedBy = "project")
    var reports: List<ProjectReport> = listOf(),

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: LocalDateTime?,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime?
)
