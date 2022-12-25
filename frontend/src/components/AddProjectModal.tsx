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
  Select,
  Stack
} from "@chakra-ui/react";
import React from "react";
import {BuildSystem, RequestProjectDto} from "../types/Project";
import ProjectsService from "../services/ProjectsService";

interface AddProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
  onOpen: () => void;
}

export const AddProjectModal: React.FC<AddProjectModalProps> = ({isOpen, onClose, onOpen}) => {

  const [project, setProject] = React.useState({
    name: "",
    defaultBranch: "main",
    url: "",
    buildSystem: BuildSystem.MAVEN
  } as RequestProjectDto);

  function handleChange(
      e:
          | React.ChangeEvent<HTMLSelectElement>
          | React.ChangeEvent<HTMLInputElement>,
      field: string,
  ) {
    setProject((prev) => ({...prev, [field]: e.target.value}));
  }

  const isDisabled = project.name === "" || project.url === "";

  const saveProject = () => {
    ProjectsService.createProject(project).then(() => {
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