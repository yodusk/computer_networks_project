import {
  Badge,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  CircularProgress,
  Heading,
  HStack,
  Spacer,
  Text
} from "@chakra-ui/react";
import React from "react";
import {ResponseReportDto} from "../types/Report";

interface ReportCardProps {
  report: ResponseReportDto
}

export const ReportCard: React.FC<ReportCardProps> = ({report}) => {

  const passedRation = report.checks.filter(check => check.passed).length / report.checks.length * 100
  return (
      <Card my={5} border="solid">
        <CardHeader>
          <HStack>
            <Heading size={"md"}>Report {report.id}</Heading>
            <Badge>{report.branchName}</Badge>
            <Spacer/>
            <Text>{report.reportTimestamp.toISOString()}</Text>
          </HStack>
        </CardHeader>
        <CardBody>
          {report.checks.map(check => (
              <HStack>
                <Text>{check.rule.name}</Text>
                <Badge
                    colorScheme={(check.passed) ? "green" : "red"}>{(check.passed) ? "PASSED" : "FAILED"}</Badge>
              </HStack>
          ))}
        </CardBody>
        <CardFooter>

          <CircularProgress size="30px" color={"green"} value={
            passedRation
          }/>
          <Text alignSelf={"center"} pl={5}>{report.checks.filter(rep => rep.passed).length}/{report.checks.length} passed</Text>
        </CardFooter>
      </Card>
  )
}