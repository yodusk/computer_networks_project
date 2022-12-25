package com.app.utils

import com.app.configuration.AppConfiguration
import com.lordcodes.turtle.ShellScript
import jakarta.inject.Singleton
import java.io.File

@Singleton
class ShellUtils(private val config: AppConfiguration) {

    fun workingDir(path: String): File {
        return File(System.getProperty("user.dir")).resolve(path)
    }

    fun workingDir(): File {
        return File(System.getProperty("user.dir"))
    }
}

fun ShellScript.rm(dir: String): String {
    return try {
        command(ShellCommands.rm, listOf(dir))
    } catch (e: Exception) {
        "Couldn't delete directory. Reason: ${e.localizedMessage}"
    }
}

fun ShellScript.mkdir(dir: String): String {
    return try {
        command(ShellCommands.mkdir, listOf(dir))
    } catch (e: Exception) {
        "Couldn't create directory. Reason: ${e.localizedMessage}"
    }
}
