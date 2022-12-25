package com.app

import com.app.utils.Shell
import com.app.utils.mkdir
import com.app.utils.rm
import com.lordcodes.turtle.shellRun
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(info = Info(title = "computernetworksproject", version = "0.0")) object Api

fun main(args: Array<String>) {

  shellRun {
    rm(Shell.workingDir)
    mkdir(Shell.workingDir)
  }

  run(*args)
}
