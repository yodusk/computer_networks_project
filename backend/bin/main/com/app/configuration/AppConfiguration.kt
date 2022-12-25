package com.app.configuration

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app")
class AppConfiguration {
  lateinit var jqassistantDir: String
  lateinit var jqassistantUrl: String
  lateinit var jqassistantConfigDir: String
  lateinit var jqassistantConfigUrl: String
  lateinit var workingDir: String
}
