/**
 * This file was auto-generated by openapi-typescript.
 * Do not make direct changes to the file.
 */


export interface paths {
  "/project": {
    get: operations["getProjects"];
    post: operations["createProject"];
  };
  "/project/check/{id}": {
    get: operations["checkProject"];
  };
  "/project/{id}": {
    get: operations["getProject"];
    post: operations["updateProject"];
    delete: operations["delete"];
  };
  "/report/project/{id}": {
    get: operations["getReports"];
  };
  "/report/{id}": {
    get: operations["getReport"];
  };
  "/rule": {
    get: operations["getAll"];
    post: operations["createRule"];
  };
  "/rule/{id}": {
    get: operations["getRule"];
    post: operations["updateRule"];
    delete: operations["deleteRule"];
  };
}

export type webhooks = Record<string, never>;

export interface components {
  schemas: {
    /** @enum {string} */
    BuildSystem: "MAVEN" | "GRADLE";
    CheckProjectRequest: {
      /** Format: int64 */
      id?: number | null;
      project: components["schemas"]["Project"] | null;
      branch: string;
      status: components["schemas"]["CheckStatus"];
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    /** @enum {string} */
    CheckStatus: "ENQUEUED" | "PROCESSING" | "PROCESSED";
    CreateRuleDto: {
      /** Format: int64 */
      id: number;
      name: string;
      query: string;
      /** Format: int64 */
      projectId: number;
    };
    Project: {
      /** Format: int64 */
      id?: number | null;
      projectName: string;
      projectUrl: string;
      buildSystem: components["schemas"]["BuildSystem"];
      defaultBranch: string;
      rules: (components["schemas"]["ProjectRule"])[];
      reports: (components["schemas"]["ProjectReport"])[];
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    ProjectReport: {
      /** Format: int64 */
      id?: number | null;
      project: components["schemas"]["Project"] | null;
      checks: (components["schemas"]["RuleResult"])[];
      /** Format: date-time */
      reportTimestamp?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    ProjectRule: {
      /** Format: int64 */
      id?: number | null;
      ruleName: string;
      ruleQuery: string;
      checks: (components["schemas"]["RuleResult"])[];
      project: components["schemas"]["Project"] | null;
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    RequestProjectDto: {
      projectName: string;
      projectUrl: string;
      defaultBranch: string;
      buildSystem: components["schemas"]["BuildSystem"];
    };
    ResponseProjectDto: {
      /** Format: int64 */
      id?: number | null;
      projectName: string;
      projectUrl: string;
      defaultBranch: string;
      buildSystem: components["schemas"]["BuildSystem"];
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    ResponseReportDto: {
      /** Format: int64 */
      id?: number | null;
      project: components["schemas"]["ResponseProjectDto"];
      checks: (components["schemas"]["ResponseRuleResultDto"])[];
      /** Format: date-time */
      reportTimestamp?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    ResponseRuleDto: {
      /** Format: int64 */
      id?: number | null;
      ruleName: string;
      ruleQuery: string;
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    ResponseRuleResultDto: {
      /** Format: int64 */
      id?: number | null;
      rule: components["schemas"]["ResponseRuleDto"];
      passed: boolean;
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
    RuleResult: {
      /** Format: int64 */
      id?: number | null;
      rule: components["schemas"]["ProjectRule"] | null;
      passed: boolean;
      /** Format: date-time */
      createdAt?: string | null;
      /** Format: date-time */
      updatedAt?: string | null;
    };
  };
  responses: never;
  parameters: never;
  requestBodies: never;
  headers: never;
  pathItems: never;
}

export type external = Record<string, never>;

export interface operations {

  getProjects: {
    responses: {
      /** @description getProjects 200 response */
      200: {
        content: {
          "application/json": (components["schemas"]["ResponseProjectDto"])[];
        };
      };
    };
  };
  createProject: {
    requestBody: {
      content: {
        "application/json": {
          project: components["schemas"]["RequestProjectDto"];
        };
      };
    };
    responses: {
      /** @description createProject 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseProjectDto"];
        };
      };
    };
  };
  checkProject: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description checkProject 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["CheckProjectRequest"];
        };
      };
    };
  };
  getProject: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description getProject 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseProjectDto"];
        };
      };
    };
  };
  updateProject: {
    parameters: {
      path: {
        id: number;
      };
    };
    requestBody: {
      content: {
        "application/json": {
          project: components["schemas"]["RequestProjectDto"];
        };
      };
    };
    responses: {
      /** @description updateProject 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseProjectDto"];
        };
      };
    };
  };
  delete: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description delete 200 response */
      200: never;
    };
  };
  getReports: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description getReports 200 response */
      200: {
        content: {
          "application/json": (components["schemas"]["ResponseReportDto"])[];
        };
      };
    };
  };
  getReport: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description getReport 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseReportDto"];
        };
      };
    };
  };
  getAll: {
    responses: {
      /** @description getAll 200 response */
      200: {
        content: {
          "application/json": (components["schemas"]["ResponseRuleDto"])[];
        };
      };
    };
  };
  createRule: {
    requestBody: {
      content: {
        "application/json": {
          rule: components["schemas"]["CreateRuleDto"];
        };
      };
    };
    responses: {
      /** @description createRule 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseRuleDto"];
        };
      };
    };
  };
  getRule: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description getRule 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseRuleDto"];
        };
      };
    };
  };
  updateRule: {
    parameters: {
      path: {
        id: number;
      };
    };
    requestBody: {
      content: {
        "application/json": {
          rule: components["schemas"]["CreateRuleDto"];
        };
      };
    };
    responses: {
      /** @description updateRule 200 response */
      200: {
        content: {
          "application/json": components["schemas"]["ResponseRuleDto"];
        };
      };
    };
  };
  deleteRule: {
    parameters: {
      path: {
        id: number;
      };
    };
    responses: {
      /** @description deleteRule 200 response */
      200: never;
    };
  };
}