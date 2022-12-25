package com.app.utils

import com.app.configuration.AppConfiguration
import com.app.configuration.Neo4jConfiguration
import com.app.entities.Project
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class JQAssistantClient(var appConf: AppConfiguration, var neo4jConf: Neo4jConfiguration) {

    @Inject lateinit var shellUtils: ShellUtils

    private val launchCmd = "./${appConf.jqassistantDir}/bin/jqassistant.sh"
    private val storeUriFlag = "-storeUri"
    private val storeUri = "${neo4jConf.uri}"
    private val storeUsernameFlag = "-storeUsername"
    private val storeUsername = "${neo4jConf.username}"
    private val storePasswordFlag = "-storePassword"
    private val storePassword = "${neo4jConf.password}"
    private val configFlag = "-p"
    private val configPath = "jqassistant.properties"
    private val projectFlag = "-f"
    private val scanCmd = "scan"
    private val analyzeCmd = "analyze"
    private val conceptsFlag = "-concepts"
    private val defaultConcepts = "classpath:Resolve"
    private val groupsFlag = "-groups"
    private val springConcepts = "spring-boot:Default"

    fun init(project: Project): String {
        var paths =
            project.scannedPaths.split(",").map { "${project.name}/${it.trim()}" }.joinToString(",")
        ProcessBuilder(
                "/bin/bash",
                "-c",
                "$launchCmd $scanCmd $storeUriFlag $storeUri $storeUsernameFlag $storeUsername $storePasswordFlag $storePassword $configFlag $configPath -continueOnError $projectFlag ${paths}")
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .directory(shellUtils.workingDir())
            .start()
            .waitFor()
        return ""
    }

    fun applyDefaultConcepts(): String {
        ProcessBuilder(
                "/bin/bash",
                "-c",
                "$launchCmd $analyzeCmd $conceptsFlag $defaultConcepts $storeUriFlag $storeUri $storeUsernameFlag $storeUsername $storePasswordFlag $storePassword -continueOnError")
            .directory(shellUtils.workingDir())
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
        return ""
    }

    fun applySpringConcepts(): String {
        ProcessBuilder(
                "/bin/bash",
                "-c",
                "$launchCmd $analyzeCmd $groupsFlag $springConcepts $storeUriFlag $storeUri $storeUsernameFlag $storeUsername $storePasswordFlag $storePassword -continueOnError")
            .directory(shellUtils.workingDir())
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
        return ""
    }
}
