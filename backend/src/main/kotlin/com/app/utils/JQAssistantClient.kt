package com.app.utils

import com.app.configuration.AppConfiguration
import com.app.configuration.Neo4jConfiguration
import com.app.entities.Project
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class JQAssistantClient(var appConf: AppConfiguration, var neo4jConf: Neo4jConfiguration) {

    @Inject lateinit var shellUtils: ShellUtils

    private val launchCmd = "${appConf.jqassistantDir}/bin/jqassistant.sh"
    private val storeUri = "-storeUri ${neo4jConf.uri}"
    private val storeUsername = "-storeUsername ${neo4jConf.username}"
    private val storePassword = "-storePassword ${neo4jConf.password}"
    private val configPath = "-p ${appConf.jqassistantConfigDir}/scan.properties"
    private val projectFlag = "-f"
    private val scanCmd = "scan"
    private val analyzeCmd = "analyze"
    private val defaultConcepts = "-concepts classpath:Resolve"
    private val springConcepts = "-groups spring-boot:Default"

    fun init(project: Project): String {
            ProcessBuilder(
                    launchCmd,
                    scanCmd,
                    storeUri,
                    storeUsername,
                    storePassword,
                    configPath,
                    projectFlag,
                    project.name,
                ).redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .directory(shellUtils.workingDir())
                .start().waitFor()
        return ""
    }

    fun applyDefaultConcepts(): String {
            ProcessBuilder(
                    launchCmd,
                    analyzeCmd,
                    defaultConcepts,
                    storeUri,
                    storeUsername,
                    storePassword,
                )
                .directory(shellUtils.workingDir()).redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start().waitFor()
        return ""
    }

    fun applySpringConcepts(): String {
            ProcessBuilder(
                    launchCmd,
                    analyzeCmd,
                    springConcepts,
                    storeUri,
                    storeUsername,
                    storePassword,
                )
                .directory(shellUtils.workingDir()).redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start().waitFor()
        return ""
    }
}
