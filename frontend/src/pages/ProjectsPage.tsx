import {
  Box,
  Button,
  Flex,
  Grid,
  Heading,
  SimpleGrid,
  Spacer,
  useDisclosure
} from "@chakra-ui/react";
import React, {useEffect} from "react";
import {ProjectStatus, ResponseProjectDto} from "../types/Project";
import {ProjectCard} from "../components/ProjectCard";
import {AddProjectModal} from "../components/AddProjectModal";
import ProjectsService from "../services/ProjectsService";

export const ProjectsPage = () => {
  const {isOpen, onOpen, onClose} = useDisclosure()
  const [projects, setProjects] = React.useState<ResponseProjectDto[]>([]);
  const [statusMap, setStatusMap] = React.useState(new Map<number, ProjectStatus>());

  useEffect(() => {
    ProjectsService.getProjects().then((projects) => {
      setProjects(projects);
      projects.forEach(
          (project) => ProjectsService.getProjectStatus(project.id).then(
              (status) => setStatusMap((prev) => new Map(prev).set(project.id, status))
          )
      )
    });
  }, [])

  return (
      <Grid backgroundColor="#151329" flexGrow={1} minH={"100vh"} alignContent={"flex-start"} p={5}>
        <AddProjectModal isOpen={isOpen} onClose={onClose} onOpen={onOpen} projects={projects} setProjects={setProjects}/>
        <Flex>
          <Heading color="white">Projects</Heading>
          <Spacer/>
          <Button onClick={onOpen}>Create new project</Button>
        </Flex>
        <Spacer py={5}/>
        <SimpleGrid columns={2} spacing={10}>
          {projects.map((project, idx) => <ProjectCard key={project.id}
                                                       status={statusMap.get(project.id)}
                                                       project={project}/>)}
        </SimpleGrid>
      </Grid>
  )
}