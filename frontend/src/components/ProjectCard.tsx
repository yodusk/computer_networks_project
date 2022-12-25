import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Heading,
  HStack,
  Spinner,
  Stack,
  Tag,
  Text, VStack
} from "@chakra-ui/react";
import React from "react";
import {BuildSystem, ProjectStatus, ResponseProjectDto} from "../types/Project";
import {useNavigate} from "react-router-dom";
import ProjectsService from "../services/ProjectsService";


export interface ProjectCardProps {
  project: ResponseProjectDto
  status?: ProjectStatus
}

export const ProjectCard: React.FC<ProjectCardProps> = ({project, status}) => {

  const navigate = useNavigate()

  const getColor = (status: ProjectStatus) => {
    switch (status) {
      case ProjectStatus.CHECKED:
        return 'green'
      case ProjectStatus.ERROR:
        return 'red'
      case ProjectStatus.IN_PROGRESS:
        return 'yellow'
      case ProjectStatus.UNKNOWN:
        return 'gray'
    }
  }

  return (
      <Card bgColor="white">
        <CardHeader>
          <Heading size='md'>{project.name}</Heading>
        </CardHeader>
        <CardBody>
          <Stack spacing='3'>
            <HStack>
            <VStack align="flex-start">
              <Text fontWeight="600">Project link:</Text>
              <Text fontWeight="600">Build system:</Text>
              <Text fontWeight="600">Branch:</Text>
              <Text fontWeight="600">Status:</Text>
            </VStack>
            <VStack
                //align right
                align="flex-end"
            >
              <Text overflowWrap={"break-word"}
              >{project.url}</Text>
              <Tag w="min-content"
                   bgColor={(project.buildSystem == BuildSystem.MAVEN) ?
                       "blue.500" : "green.500"}
                   color="white"
              >
                {project.buildSystem}
              </Tag>
              <Tag bgColor="yellow.200">{project.defaultBranch}</Tag>
              <Tag w="min-content" bgColor={getColor(status as ProjectStatus)}><Text >{status}</Text></Tag>)
            </VStack>
            </HStack>
          </Stack>
        </CardBody>
        <CardFooter
            display={"flex"}
        alignItems={"flex-end"}
        justifyContent={"flex-end"}
        >
          <ButtonGroup spacing='2'>
            <Button variant='outline' colorScheme='blue'
                    onClick={() => navigate("/projects/" + project.id)}>
              To project
            </Button>
            <Button variant='solid' colorScheme='blue'
                    isLoading={status == ProjectStatus.IN_PROGRESS}
                    loadingText={"Checking..."}
                    onClick={() => ProjectsService.checkProject(project.id, project.defaultBranch)}>
              Recheck
            </Button>
          </ButtonGroup>
        </CardFooter>
      </Card>
  )
}