import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Code,
  Heading,
  Spacer,
  Text,
  useDisclosure
} from "@chakra-ui/react";
import {ResponseRuleDto} from "../types/Rule";
import React from "react";
import {ModifyRuleModal} from "./ModifyRuleModal";
import RuleService from "../services/RuleService";

interface RuleCardProps {
  rule: ResponseRuleDto

  rules: ResponseRuleDto[]
  setRules: React.Dispatch<React.SetStateAction<ResponseRuleDto[]>>
}

export const RuleCard: React.FC<RuleCardProps> = ({rule, setRules, rules}) => {
  const {isOpen, onOpen, onClose} = useDisclosure()
  return (
      <Card my={5} border="solid">
        <ModifyRuleModal isOpen={isOpen} onClose={onClose} onOpen={onOpen} rule={rule} setRule={
          (rule) => {
            setRules(rules.map((r) => {
              if (r.id === rule.id) {
                return rule
              } else {
                return r
              }
            }))
          }
        }/>
        <CardHeader><Heading size={"md"}>{rule.name}</Heading></CardHeader>
        <CardBody>
          <Text>{rule.description}</Text>
          <Spacer py={4}/>
          <Code>{rule.query}</Code>
        </CardBody>
        <CardFooter>
          <ButtonGroup spacing='2'>
            <Button variant='outline' size="sm" colorScheme='red'
                    onClick={
                      () => {
                        RuleService.deleteRule(rule.id as number).then(() => {
                          setRules(rules.filter(r => r.id !== rule.id))
                        })
                      }
                    }
            >
              Delete
            </Button>
            <Button variant='outline' size="sm" colorScheme='blue' onClick={onOpen}>Modify</Button>
          </ButtonGroup>
        </CardFooter>
      </Card>
  )
}