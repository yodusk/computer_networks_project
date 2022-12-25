CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE check_requests
(
    id         BIGINT       NOT NULL,
    project_id BIGINT,
    branch     VARCHAR(255) NOT NULL,
    status     INTEGER      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_check_requests PRIMARY KEY (id)
);

CREATE TABLE projects
(
    id             BIGINT       NOT NULL,
    project_name   VARCHAR(255) NOT NULL,
    project_url    VARCHAR(255) NOT NULL,
    build_system   INTEGER,
    default_branch VARCHAR(255),
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_projects PRIMARY KEY (id)
);

CREATE TABLE reports
(
    id               BIGINT       NOT NULL,
    project_id       BIGINT,
    branch_name      VARCHAR(255) NOT NULL,
    report_timestamp TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_reports PRIMARY KEY (id)
);

CREATE TABLE rule_result
(
    id         BIGINT  NOT NULL,
    rule_id    BIGINT,
    passed     BOOLEAN NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_rule_result PRIMARY KEY (id)
);

CREATE TABLE rules
(
    id               BIGINT       NOT NULL,
    rule_name        VARCHAR(255) NOT NULL,
    rule_query       VARCHAR(1000000) NOT NULL,
    rule_description VARCHAR(255),
    project_id       BIGINT,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_rules PRIMARY KEY (id)
);

ALTER TABLE projects
    ADD CONSTRAINT uc_projects_project_name UNIQUE (project_name);

ALTER TABLE check_requests
    ADD CONSTRAINT FK_CHECK_REQUESTS_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id);

ALTER TABLE reports
    ADD CONSTRAINT FK_REPORTS_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id);

ALTER TABLE rules
    ADD CONSTRAINT FK_RULES_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id);

ALTER TABLE rule_result
    ADD CONSTRAINT FK_RULE_RESULT_ON_RULE FOREIGN KEY (rule_id) REFERENCES rules (id);

-- insert one project
INSERT INTO projects (id, project_name, project_url, build_system, default_branch, created_at, updated_at)
VALUES (1, 'test_project', 'git@github.com:yodusk/PiggyMetrics.git', 0, 'master', now(), now());

-- alter hibernate_sequence
ALTER SEQUENCE hibernate_sequence RESTART WITH 2;

INSERT INTO rules (id, rule_name, rule_query, rule_description, project_id, created_at, updated_at)
VALUES (1, 'Each microservice has error handler',
        'MATCH (service:Microservice)--(cls:Class)-[:ANNOTATED_BY]->(ann:Annotation)-[:OF_TYPE]->(:Type{name:"RestController"}) ' ||
        'MATCH (service:Microservice)--(errorHandler:Class)-[:ANNOTATED_BY]->(ann2:Annotation)-[:OF_TYPE]->(:Type{name:"ControllerAdvice"}) ' ||
        'with collect(service.serviceName) as list_with_handler ' ||
        'match (service:Microservice) ' ||
        'where not service.serviceName in list_with_handler ' ||
        'return service as service_without_handler',
        'Every microservice should have error handler in order ' ||
        'not to pass errors to user as it is inconvenient and may expose security threats', 1, now(), now()),
       (2, 'No shared mongo db collections', 'match (entity:MongoDb:Class)--(a:Artifact) ' ||
                                             'with entity.collectionName as collection, count(a) as used_by_count  ' ||
                                             'where used_by_count > 1 return collection, used_by_count',
        'Shared database is an anti pattern as it violates data integrity', 1, now(),
        now()),
       (3, 'Controller does not access DAO layer', 'match (controller:RestController)--(a:Field) ' ||
                                                   'where (a.name  CONTAINS apoc.text.clean("Repository") or a.name CONTAINS "Repository") ' ||
                                                   'return controller as ControllerAccessingRepository',
        'Controller should not access DAO level directly as it exposes api to vulnerabilitites', 1, now(),
        now()),
       (4, 'Each service has health endpoint',
        'match (service:Microservice)--(controller:RestController)--(endpoint:Endpoint) ' ||
        'where endpoint.url contains "/health" with collect(service.serviceName) as list_with_health  ' ||
        'match (service:Microservice) where not service.serviceName in list_with_health ' ||
        'return service as ServiceMissingHealthEndpoint',
        'Having health endpoint is crucial to check microservice state in runtime and recover service as fast as possible',
        1, now(), now()),
       (5, 'Api methods are compatible by methods signatures',
        'match (feignMethod:FeignMethod)-[:INVOKES_REMOTE]-(controllerMethod:Endpoint) ' ||
        'optional match (feignMethod:FeignMethod)-[:HAS]-(p1:Parameter)-[:OF_TYPE]-(t1:Type) ' ||
        'optional match (controllerMethod:Endpoint)-[:HAS]-(p2:Parameter)-[:OF_TYPE]-(t2:Type) ' ||
        'optional match (feignMethod:FeignMethod)-[:RETURNS]-(r1:Type) ' ||
        'optional match (controllerMethod:Endpoint)-[:RETURNS]-(r2:Type) with feignMethod.name as feignMethodName, ' ||
        'collect(distinct t1.fqn) as feignParams, r1.fqn as feignReturns, controllerMethod.name as controllerMethodName, ' ||
        'collect(distinct t2.fqn) as controllerParams, r2.fqn as controllerReturns ' ||
        'where not feignParams=controllerParams and feignReturns=controllerReturns ' ||
        'return feignMethodName, feignParams, feignReturns, controllerMethodName, controllerParams, controllerReturns',
        'Api methods should have compatible signatures', 1, now(), now());

-- insert into check_requests
-- restart hibernate_sequence
ALTER SEQUENCE hibernate_sequence RESTART WITH 6;