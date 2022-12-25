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
  idx: number
}

export const ReportCard: React.FC<ReportCardProps> = ({report, idx}) => {

  const passedRation = report.checks.filter(check => check.passed).length / report.checks.length * 100
  return (
      <Card my={5} border="solid">
        <CardHeader>
          <HStack>
            <Heading size={"md"}>Report {idx}</Heading>
            <Spacer/>
            <Text>{new Date(report.updatedAt).toISOString()}</Text>
          </HStack>
          <Spacer py={2}/>
          <HStack>
            <Text>Branch: </Text>
            <Badge>{report.branchName}</Badge>
          </HStack>
        </CardHeader>
        <CardBody>
          {report.checks.sort(
              (a, b) => a.passed === b.passed ? 0 : a.passed ? -1 : 1
          ).map((check, idx) => (
              <HStack key={idx}>
                <Badge
                    w="60px"
                    alignItems={"center"}
                    justifyContent={"center"}
                    colorScheme={(check.passed) ? "green" : "red"}>{(check.passed) ? "PASSED" : "FAILED"}</Badge>
                <Text>{check.rule.name}</Text>
              </HStack>
          ))}
        </CardBody>
        <CardFooter>

          <CircularProgress size="30px" color={"green"} value={
            passedRation
          }/>
          <Text alignSelf={"center"}
                pl={5}>{report.checks.filter(rep => rep.passed).length}/{report.checks.length} passed</Text>
        </CardFooter>
      </Card>
  )
}