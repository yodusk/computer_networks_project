package com.app.configuration

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("neo4j")
class Neo4jConfiguration {
  var uri: String? = null
  var username: String? = null
  var password: String? = null
}
