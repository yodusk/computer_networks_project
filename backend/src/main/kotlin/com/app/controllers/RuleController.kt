package com.app.controllers

import com.app.dto.rule.CreateRuleDto
import com.app.dto.rule.ResponseRuleDto
import com.app.services.ProjectRuleService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.parameters.RequestBody
import jakarta.inject.Inject

@Controller("/rule")
class RuleController {

  @Inject lateinit var ruleService: ProjectRuleService

  @Post
  fun createRule(@RequestBody rule: CreateRuleDto): ResponseRuleDto {
    return ruleService.create(rule)
  }

  @Post("/{id}")
  fun updateRule(@PathVariable id: Long, @RequestBody rule: CreateRuleDto): ResponseRuleDto {
    return ruleService.update(id, rule)
  }

  @Get("/{id}")
  fun getRule(@PathVariable id: Long): ResponseRuleDto {
    return ruleService.getById(id)
  }

  @Get("/project/{id}")
    fun getRules(@PathVariable id: Long): List<ResponseRuleDto> {
        return ruleService.getByProjectId(id)
    }

  @Get
  fun getAll(): List<ResponseRuleDto> {
    return ruleService.getAll()
  }

  @Delete("/{id}")
  fun deleteRule(@PathVariable id: Long) {
    ruleService.delete(id)
  }
}
