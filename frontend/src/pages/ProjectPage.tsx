import React, {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {
  Box,
  Button,
  Flex,
  Heading,
  HStack,
  Spacer,
  Spinner,
  Stack,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
  Tag,
  Text,
  useDisclosure, VStack
} from "@chakra-ui/react";
import {BuildSystem, ProjectStatus, ResponseProjectDto} from "../types/Project";
import {IconArrowLeft, IconCodePlus, IconStatusChange} from "@tabler/icons";
import {ResponseRuleDto} from "../types/Rule";
import {RuleCard} from "../components/RuleCard";
import {ResponseReportDto} from "../types/Report";
import {ReportCard} from "../components/ReportCard";
import {ModifyProjectModal} from "../components/ModifyProjectModal";
import {AddRuleModal} from "../components/AddRuleModal";
import ProjectsService from "../services/ProjectsService";


export const ProjectPage: React.FC = () => {
  const {id} = useParams();
  const {
    isOpen: isOpenModifyProject,
    onOpen: onOpenModifyProject,
    onClose: onCloseModifyProject
  } = useDisclosure()
  const {isOpen: isOpenAddRule, onOpen: onOpenAddRule, onClose: onCloseAddRule} = useDisclosure()
  const [project, setProject] = React.useState<ResponseProjectDto | null >(null);
  const [status, setStatus] = React.useState<ProjectStatus>();
  const [isLoading, setIsLoading] = React.useState(true);
  const [rules, setRules] = React.useState<ResponseRuleDto[]>([]);
  const [reports, setReports] = React.useState<ResponseReportDto[]>([]);

  useEffect(() => {
    setIsLoading(true);
    ProjectsService.getProjectById(id as string).then((project) => {
      ProjectsService.getProjectStatus(project.id).then((status) => {
        ProjectsService.getRulesByProjectId(project.id).then((rules) => {
          ProjectsService.getReportsByProjectId(project.id).then((reports) => {
            setReports(reports);
            setRules(rules)
            setProject(project);
            setStatus(status);
            setIsLoading(false);
          })
        })
      })
    })
  }, [])



  const getColor = (status: ProjectStatus) => {
    switch (status) {
      case ProjectStatus.CHECKED:
        return 'green'
      case ProjectStatus.ERROR:
        return 'red'
      case ProjectStatus.IN_PROGRESS:
        return 'orange'
      case ProjectStatus.UNKNOWN:
        return 'gray'
    }
  }

  const navigate = useNavigate()

  if (isLoading || project === null) {
    return <Spinner />
  } else {
    return (
        <Box backgroundColor="#FFFF" w="100%" h="100vh" p={5}>
          <ModifyProjectModal isOpen={isOpenModifyProject} onClose={onCloseModifyProject}
                              onOpen={onOpenModifyProject} project={project}/>
          <AddRuleModal isOpen={isOpenAddRule} onClose={onCloseAddRule} onOpen={onOpenAddRule}
                        projectId={project.id} rules={rules} setRules={setRules}/>
          <Flex>
            <Heading>{project.name}</Heading>
            <Spacer/>
            <HStack spacing={5}>
              <Button variant="solid" leftIcon={<IconCodePlus/>} onClick={onOpenAddRule}>Add
                rule</Button>
              <Button variant="solid" leftIcon={<IconStatusChange/>}
                      onClick={onOpenModifyProject}>Modify</Button>
              <Button variant="outline" leftIcon={<IconArrowLeft/>}
                      onClick={() => navigate("/projects")}>Back to projects</Button>
            </HStack>
          </Flex>
          <Spacer py={3}/>
          <Stack spacing='3'>
            <HStack>
              <Text fontWeight="600">Project link:</Text>
              <Text>{project.url}</Text>
            </HStack>
            <HStack>
              <Text fontWeight="600">Build system:</Text>
              <Tag w="min-content"
                   bgColor={(project.buildSystem == BuildSystem.MAVEN) ?
                       "blue.500" : "green.500"}
                   color="white"
              >
                {project.buildSystem}
              </Tag>
            </HStack>
            <HStack>
              <Text fontWeight="600">Branch:</Text>
              <Tag w="min-content" bgColor="yellow.200">{project.defaultBranch}</Tag>
            </HStack>
            <HStack>
              <Text fontWeight="600">Status:</Text>
              <Tag w="min-content" bgColor={getColor(status as ProjectStatus)}
                   color="white"><Text
                  pr={5}>{status}</Text> {status == ProjectStatus.IN_PROGRESS && (
                  <Spinner size="sm"/>)}</Tag>)
            </HStack>
            <VStack
            align={"stretch"}
            >

              <Text fontWeight="600">Scanned paths:</Text>
              {project.paths.split(",").map((path) => (
                  <Tag key={path} bgColor="gray.200">{path}</Tag>
              ))}
            </VStack>
            <Tabs>
              <TabList>
                <Tab>Rules</Tab>
                <Tab>Reports</Tab>
              </TabList>

              <TabPanels overflowY="scroll" h="65vh">
                <TabPanel>
                  {rules.map((rule) => (<RuleCard key={rule.id} rule={rule} rules={rules} setRules={setRules}/>))}
                </TabPanel>
                <TabPanel>
                  {reports.map((report, idx) => (<ReportCard key={idx} report={report} idx={idx}/>))}
                </TabPanel>
              </TabPanels>
            </Tabs>
          </Stack>
        </Box>
    )
  }
}