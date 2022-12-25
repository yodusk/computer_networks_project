package com.app.services

import com.app.configuration.AppConfiguration
import com.app.entities.BuildSystem.MAVEN
import com.app.entities.Project
import com.app.utils.ShellUtils
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ProjectBuilderService(private val config: AppConfiguration) {

    @Inject lateinit var shellUtils: ShellUtils

    private val mvn = "mvn"

    private val mvnBuild = "clean package"

    private val mvnSkipTests = "-DskipTests"

    private val gradleInstall = "install"

    private val gradleSkipTests = "-x test"

    private val gradle = "./gradlew"

    private val origin = "origin"

    @Transactional
    open fun initialiseProject(project: Project): Project {
        println("Initialising project ${project.name}")
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "rm -rf " + project.name)
            .directory(shellUtils.workingDir())
            .start()
            .waitFor()

        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "mkdir " + project.name)
            .directory(shellUtils.workingDir())
            .start()
            .waitFor()
        println("Made dirs")
        println("Cloning project ${project.name}")
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "git clone " + project.url + " " + project.name)
            .directory(shellUtils.workingDir())
            .start()
            .waitFor()
        println("Cloned project ${project.name}")
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "git checkout " + project.defaultBranch)
            .directory(shellUtils.workingDir(project.name))
            .start()
            .waitFor()
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "git pull origin " + project.defaultBranch)
            .directory(shellUtils.workingDir(project.name))
            .start()
            .waitFor()
        println("Checked out branch ${project.defaultBranch}")
        println("Project initialised")
        return project
    }

    @Transactional
    open fun syncProject(project: Project, branchName: String): String {
        println("Syncing project")
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "git checkout " + branchName)
            .directory(shellUtils.workingDir(project.name))
            .start()
            .waitFor()
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "git pull origin " + branchName)
            .directory(shellUtils.workingDir(project.name))
            .start()
            .waitFor()
        return ""
    }

    @Transactional
    open fun buildProject(project: Project): String {
        println("Building project")
        if (project.buildSystem == MAVEN) {
            ProcessBuilder()
                .redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .command("/bin/bash", "-c", "$mvn $mvnBuild $mvnSkipTests")
                .directory(shellUtils.workingDir(project.name))
                .start()
                .waitFor()
        } else {
            ProcessBuilder()
                .redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .command("/bin/bash", "-c", "$gradle $gradleInstall $gradleSkipTests")
                .directory(shellUtils.workingDir(project.name))
                .start()
                .waitFor()
        }
        return ""
    }

    @Transactional
    open fun projectCleanUp(project: Project): String {
        println("Cleaning up project")
        ProcessBuilder()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .command("/bin/bash", "-c", "rm -rf " + project.name)
            .directory(shellUtils.workingDir())
            .start()
            .waitFor()
        return ""
    }
}
