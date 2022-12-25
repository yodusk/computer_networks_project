export interface CreateRuleDto {
  id: number,
  name: string,
  query: string,
  description: string,
  projectId: number
}

export interface ResponseRuleResultDto {
  id: number,
  rule: ResponseRuleDto,
  passed: Boolean,
  createdAt: Date,
  updatedAt: Date
}

export interface ResponseRuleDto {
  id?: number;
  name: string;
  query: string;
  description: string;
  projectId: number;
  createdAt?: Date;
  updatedAt?: Date;
}