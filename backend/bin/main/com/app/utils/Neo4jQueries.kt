package com.app.utils

class Neo4jQueries {
    companion object {
        const val deleteAllQuery = "MATCH (n) DETACH DELETE n"
        const val tagMicroservicesRule =
            "MATCH (a:Artifact)--(cls:Class)-[:ANNOTATED_BY]->(ann:Annotation)-[:OF_TYPE]->(:Type{name:\"RestController\"}) " +
                "SET a:Microservice SET a.serviceName = reverse(split(a.fileName, '/'))[0] " +
                "RETURN a"
        const val tagEndpoints =
            "MATCH (cls:Class)-[:DECLARES]->(endpoint)-[:ANNOTATED_BY]->(ann:Annotation)-[:OF_TYPE]->(:Type{name:\"RequestMapping\"}) " +
                "WHERE cls.fqn starts with 'com.' " +
                "OPTIONAL MATCH (ann)-[:HAS]->(:Value{name:\"value\"})-[:CONTAINS]->(url:Value) " +
                "OPTIONAL MATCH (ann)-[:HAS]->(:Value{name:\"path\"})-[:CONTAINS]->(path:Value) " +
                "OPTIONAL MATCH (ann)-[:HAS]->(:Value{name:\"method\"})-[:CONTAINS]->()-[:IS]->(httpMethod:Field) " +
                "OPTIONAL MATCH (cls)-[:ANNOTATED_BY]->(classMapping:Annotation)-[:OF_TYPE]->(Type{name:\"RequestMapping\"}),(classMapping)-[:HAS]->(:Value{name:\"value\"})-[:CONTAINS]->(classLevelUrl:Value) " +
                "SET endpoint:Endpoint SET endpoint.method=split(httpMethod.signature, \" \")[1] " +
                "SET endpoint.url=coalesce(classLevelUrl.value, '') + coalesce(url.value, '') + coalesce(path.value, '') " +
                "RETURN cls.fqn, endpoint.url, endpoint.method"
        const val tagFeignClients =
            "MATCH (client:Interface)-[:DECLARES]->(m:Method) " +
                "WHERE client.fqn STARTS WITH \"com.\" " +
                "AND (client)-[:ANNOTATED_BY]->()-[:OF_TYPE]->(:Type{fqn:\"org.springframework.cloud.openfeign.FeignClient\"}) " +
                "SET client:FeignClient " +
                "return *"
        const val tagFeignMethods =
            "MATCH (client:FeignClient)-[:DECLARES]->(m:Method) " +
                "MATCH (m)-[:ANNOTATED_BY]->(ann:Annotation)-[:HAS]->(:Value{name:\"value\"})-[:CONTAINS]->(url:Value) " +
                "MATCH (m)-[:ANNOTATED_BY]->(ann:Annotation)-[:HAS]->(:Value{name:\"method\"})-[:CONTAINS]->()-[:IS]->(httpMethod:Field) " +
                "SET m:FeignMethod SET m.url = apoc.text.regreplace(url.value, '\\\\{.*\\\\}', '{}') " +
                "SET m.httpMethod = split(httpMethod.signature, ' ')[1] " +
                "return m.name, m.httpMethod, m.url"
        const val enrichUrls =
            "MATCH (configJar:Artifact) WHERE configJar.fileName CONTAINS 'config.jar' " +
                "MATCH (configJar)-[:CONTAINS]->(f:File:YAML)-[*]->(k:YAML:Key{fqn: 'server.servlet.context-path'})--(path:Value) WITH reverse(split(replace(f.fileName, '.yml', ''), '/'))[0] as serviceName, path.value as urlPrefix " +
                "MATCH (serviceJar:Artifact)-[:CONTAINS]->(f:File:YAML)-[*]->(sn:YAML:Key{fqn: 'spring.application.name'})--(appName:Value) WHERE appName.value = serviceName " +
                "MATCH (serviceJar)-[:CONTAINS|DECLARES*..2]->(endpoint:Endpoint) " +
                "SET endpoint.fullUrl = urlPrefix + apoc.text.regreplace(endpoint.url, '\\\\{.*\\\\}', '{}') " +
                "RETURN distinct serviceName, endpoint.url, endpoint.fullUrl"
        const val linkFeigns =
            "MATCH (client:FeignMethod), (endpoint:Endpoint) " +
                "WHERE client.url=endpoint.fullUrl AND client.httpMethod=endpoint.method " +
                "MERGE (client)-[:INVOKES_REMOTE]->(endpoint) " +
                "RETURN client.url, endpoint.fullUrl"
        const val daoFromMongo =
            "MATCH (entity:Type)-[:ANNOTATED_BY]->(ann:Annotation) " +
                "MATCH (ann)-[:OF_TYPE]-(:Type{fqn:'org.springframework.data.mongodb.core.mapping.Document'}) " +
                "MATCH (ann)-[:HAS]->(collection:Value{name:\"collection\"}) " +
                "SET entity:Entity:MongoDb SET entity.collectionName=collection.value " +
                "RETURN entity.fqn"
    }
}
