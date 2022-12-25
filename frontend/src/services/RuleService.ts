import {CreateRuleDto, ResponseRuleDto} from "../types/Rule";
import {serviceUrl} from "./config";

class RuleService {
  async createRule(rule: CreateRuleDto) {
    const res = await fetch(`${serviceUrl}/rule`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(rule)
    })
    return await res.json() as Promise<ResponseRuleDto>
  }

  async deleteRule (id: number) {
    const res = await fetch(`${serviceUrl}/rule/${id}`, {
      method: 'DELETE'
    })
  }

  async updateRule(id: number, rule: CreateRuleDto){
    console.log(rule)
    const res = await fetch(`${serviceUrl}/rule/${id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(rule)
    })
    return await res.json() as Promise<ResponseRuleDto>
  }
}

export default new RuleService()