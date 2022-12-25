package com.app.repositories

import com.app.entities.Project
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface ProjectRepository : CrudRepository<Project, Long> {
  fun existsByUrl(projectUrl: String): Boolean
  fun existsByName(projectName: String): Boolean
}
