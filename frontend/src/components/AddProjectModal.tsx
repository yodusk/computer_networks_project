import {
  Button,
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Select, Spacer,
  Stack
} from "@chakra-ui/react";
import React from "react";
import {BuildSystem, RequestProjectDto, ResponseProjectDto} from "../types/Project";
import ProjectsService from "../services/ProjectsService";

interface AddProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
  onOpen: () => void;

  projects: ResponseProjectDto[];
  setProjects: React.Dispatch<React.SetStateAction<ResponseProjectDto[]>>;
}

export const AddProjectModal: React.FC<AddProjectModalProps> = ({isOpen, onClose, onOpen, projects, setProjects}) => {

  const [project, setProject] = React.useState({
    name: "",
    defaultBranch: "main",
    url: "",
    buildSystem: BuildSystem.MAVEN
  } as RequestProjectDto);

  const [paths, setPaths] = React.useState<string[]>([""]);

  function handleChange(
      e:
          | React.ChangeEvent<HTMLSelectElement>
          | React.ChangeEvent<HTMLInputElement>,
      field: string,
  ) {
    setProject((prev) => ({...prev, [field]: e.target.value}));
  }

  const isDisabled = project.name === "" || project.url === "" || paths.every((path) => path === "");

  const saveProject = () => {
    project.paths = paths.join(",");
    ProjectsService.createProject(project).then((created) => {
      setProjects([...projects, created]);
      project.name = "";
      project.url = "";
      project.defaultBranch = "main";
      project.buildSystem = BuildSystem.MAVEN;
      setPaths([""]);
      onClose();
    });
  }

  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          <ModalHeader>Create project</ModalHeader>
          <ModalCloseButton/>
          <ModalBody flexDirection="column">
            <Stack spacing={5}>
              <FormControl>
                <FormLabel>Name</FormLabel>
                <Input
                    type="text"
                    value={project.name}
                    onChange={(e) => handleChange(e, "name")}
                />
              </FormControl>
              <FormControl>
                <FormLabel>Branch</FormLabel>
                <Input
                    type="text"
                    value={project.defaultBranch}
                    onChange={(e) => handleChange(e, "defaultBranch")}
                />
              </FormControl>
              <FormControl>
                <FormLabel>URL</FormLabel>
                <Input
                    type="text"
                    value={project.url}
                    onChange={(e) => handleChange(e, "url")}
                />
              </FormControl>
              <FormControl>
                <FormLabel>Provider</FormLabel>
                <Select
                    value={project.buildSystem}
                    onChange={(e) => handleChange(e, "buildSystem")}
                >
                  <option value={BuildSystem.GRADLE}>Gradle</option>
                  <option value={BuildSystem.MAVEN}>Maven</option>
                </Select>
              </FormControl>
              <FormControl
                  display="flex"
                  flexDirection="column"
                  alignItems="flex-start"
              >
                <FormLabel>Paths</FormLabel>
              </FormControl>
              {paths.map((path, index) => (
                  <FormControl key={index}
                                display="flex"
                                flexDirection="row"
                                alignItems="center"
                  >
                    <Input
                        type="text"
                        value={path}
                        onChange={(e) => {
                          const newPaths = [...paths];
                          newPaths[index] = e.target.value;
                          setPaths(newPaths);
                        }}
                    />
                    <Spacer py={2}/>
                    <Button
                        colorScheme={"red"}
                        onClick={() => {
                          const newPaths = [...paths];
                          newPaths.splice(index, 1);
                          setPaths(newPaths);
                        }}
                    >
                      Remove
                    </Button>
                  </FormControl>
              ))}
              <Button
                  onClick={() => {
                    setPaths((prev) => [...prev, ""]);
                  }
                  }
              >
                Add path
              </Button>
            </Stack>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" mr={3} onClick={onClose}>
              Close
            </Button>
            <Button variant="solid" bgColor="blue.400" color="white" isDisabled={
              isDisabled
            }
                    onClick={saveProject}
            >
              Create
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>)
}