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
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "rules")
@Serdeable.Deserializable
@Serdeable.Serializable
class ProjectRule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "rule_name", nullable = false)
    var name: String,

    @Column(name = "rule_query", nullable = false)
    var query: String,

    @Column(name = "rule_description")
    val description: String = "",

    @OneToMany(mappedBy = "rule", fetch = FetchType.EAGER)
    var checks: List<RuleResult>,

    @ManyToOne
    var project: Project,

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: LocalDateTime?,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime?
)
