import {ProjectStatus, RequestProjectDto, ResponseProjectDto} from "../types/Project";
import {serviceUrl} from "./config";
import {ResponseReportDto} from "../types/Report";
import {ResponseRuleDto} from "../types/Rule";

class ProjectsService {

  baseUrl = serviceUrl + '/project'

  getProjects = async () => {
    const res = await fetch(this.baseUrl)
    return await res.json() as Promise<ResponseProjectDto[]>
  }

  getProjectById = async (id: string) => {
    const res = await fetch(`${this.baseUrl}/${id}`)
    return await res.json() as Promise<ResponseProjectDto>
  }

  createProject = async (dto: RequestProjectDto) => {
    const res = await fetch(this.baseUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dto)
    })
    return await res.json() as Promise<ResponseProjectDto>
  }

  updateProject = async (id: number, dto: RequestProjectDto) => {
    const res = await fetch(`${this.baseUrl}/${id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dto)
    }).then(
        res => res.json()
    ).catch(e => console.log(e))
    return await res as Promise<ResponseProjectDto>
  }

  async getProjectStatus(id: number){
    const res1 = await fetch(`${serviceUrl}/project/check/${id}`)
    const checks = await res1.json()
    if (checks.length > 0) {
      const lastCheck = checks[checks.length - 1]
      if (lastCheck.status === 'ENQUEUED') {
        return ProjectStatus.IN_PROGRESS
      }
    }
    // fetch reports
    const res = await fetch(`${serviceUrl}/report/project/${id}`)
    const reports: ResponseReportDto[] = await res.json()
    console.log(reports)
    if (reports.length === 0) {
      return ProjectStatus.UNKNOWN
    } else {
      const lastReport = reports[reports.length - 1]
      // console.log(lastReport)
      if (lastReport.checks.every(check => check.passed)) {
        return ProjectStatus.CHECKED
      } else {
        return ProjectStatus.ERROR
      }
    }
  }

  checkProject = async (id: number | undefined, defaultBranch: string) => {
    const res = await fetch(`${serviceUrl}/project/check/${id}/${defaultBranch}`, {
      method: 'POST'
    })
    console.log(res)
  }

  async getRulesByProjectId(id: number) {
    const res = await fetch(`${serviceUrl}/rule/project/${id}`)
    return await res.json() as Promise<ResponseRuleDto[]>
  }

  async getReportsByProjectId(id: number) {
    const res = await fetch(`${serviceUrl}/report/project/${id}`)
    return await res.json() as Promise<ResponseReportDto[]>
  }
}

export default new ProjectsService()