package com.app.services

import com.app.configuration.AppConfiguration
import com.app.entities.Project
import com.app.utils.JQAssistantClient
import com.app.utils.Neo4jQueries.Companion.daoFromMongo
import com.app.utils.Neo4jQueries.Companion.deleteAllQuery
import com.app.utils.Neo4jQueries.Companion.enrichUrls
import com.app.utils.Neo4jQueries.Companion.linkFeigns
import com.app.utils.Neo4jQueries.Companion.tagEndpoints
import com.app.utils.Neo4jQueries.Companion.tagFeignClients
import com.app.utils.Neo4jQueries.Companion.tagFeignMethods
import com.app.utils.Neo4jQueries.Companion.tagMicroservicesRule
import com.app.utils.ShellUtils
import com.lordcodes.turtle.shellRun
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.neo4j.driver.Driver
import javax.transaction.Transactional

@Singleton
open class JQAssistantService(private val config: AppConfiguration) {

    @Inject lateinit var jqAssistantClient: JQAssistantClient

    @Inject lateinit var driver: Driver

    @Inject lateinit var shellUtils: ShellUtils

    private val dataEnrichRulesList =
        listOf(
            tagMicroservicesRule,
            tagEndpoints,
            tagFeignClients,
            tagFeignMethods,
            enrichUrls,
            linkFeigns,
            daoFromMongo)

    @Transactional
    open fun prepareProjectForRules(project: Project) {
        this.clearDatasource()
        println("Running jqAssistant")
        jqAssistantClient.init(project)
        jqAssistantClient.applyDefaultConcepts()
        jqAssistantClient.applySpringConcepts()
        this.addCustomConcepts()
    }

    @Transactional
    open fun addCustomConcepts() {
        driver.session().let { dataEnrichRulesList.map(it::run) }
    }

    @Transactional
    open fun clearDatasource() {
        val session = driver.session()
        session.run(deleteAllQuery)
    }
}
