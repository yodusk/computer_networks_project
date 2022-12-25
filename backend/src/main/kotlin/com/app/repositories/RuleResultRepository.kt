package com.app.repositories

import com.app.entities.RuleResult
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface RuleResultRepository : CrudRepository<RuleResult, Long>