import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Code, Heading,
  Text, useDisclosure
} from "@chakra-ui/react";
import {ResponseRuleDto} from "../types/Rule";
import React from "react";
import {ModifyRuleModal} from "./ModifyRuleModal";

interface RuleCardProps {
  rule: ResponseRuleDto
}

export const RuleCard: React.FC<RuleCardProps> = ({rule}) => {
  const {isOpen, onOpen, onClose} = useDisclosure()
  return (
      <Card my={5} border="solid">
        <ModifyRuleModal isOpen={isOpen} onClose={onClose} onOpen={onOpen}/>
        <CardHeader><Heading size={"md"}>{rule.name}</Heading></CardHeader>
        <CardBody>
          <Text>{rule.description}</Text>
          <Code>{rule.query}</Code>
        </CardBody>
        <CardFooter>
          <ButtonGroup spacing='2'>
            <Button variant='outline' size="sm" colorScheme='cyan' onClick={onOpen}>
              Modify
            </Button>
            <Button variant='outline' size="sm" colorScheme='red'>
              Delete
            </Button>
          </ButtonGroup>
        </CardFooter>
      </Card>
  )
}