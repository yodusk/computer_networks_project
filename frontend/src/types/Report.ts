import {ResponseProjectDto} from "./Project";
import {ResponseRuleResultDto} from "./Rule";

export interface ResponseReportDto {
  id: number,
  project: ResponseProjectDto,
  checks: ResponseRuleResultDto[],
  branchName: String,
  reportTimestamp: Date,
  updatedAt: Date
}