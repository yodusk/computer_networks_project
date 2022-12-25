export interface CheckProjectDto {
  projectId: number;
  branchName: string;
}

export interface RequestProjectDto {
  name: string;
  url: string;
  defaultBranch: string;

  paths: string;
  buildSystem: BuildSystem;
}

export enum BuildSystem {
  MAVEN = 'MAVEN',
  GRADLE = 'GRADLE',
}

export interface ResponseProjectDto {
  id: number;
  name: string;
  url: string;
  defaultBranch: string;
  buildSystem: BuildSystem;
  paths: string;
  createdAt?: Date;
  updatedAt?: Date;
}

export enum ProjectStatus {
  CHECKED = 'CHECKED',
  IN_PROGRESS = 'IN_PROGRESS',
  ERROR = 'ERROR',
  UNKNOWN = 'UNKNOWN',
}