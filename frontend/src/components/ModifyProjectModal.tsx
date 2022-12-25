import {
  Button, Flex,
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
  Stack, Textarea, VStack
} from "@chakra-ui/react";
import React from "react";
import {BuildSystem, ResponseProjectDto} from "../types/Project";
import ProjectsService from "../services/ProjectsService";

interface AddProjectModalProps {
  project: ResponseProjectDto;
  isOpen: boolean;
  onClose: () => void;
  onOpen: () => void;
}

export const ModifyProjectModal: React.FC<AddProjectModalProps> = ({
                                                                     project,
                                                                     isOpen,
                                                                     onClose,
                                                                     onOpen
                                                                   }) => {
  const [name, setName] = React.useState<string>(project.name);
  const [url, setUrl] = React.useState<string>(project.url);
  const [defaultBranch, setDefaultBranch] = React.useState<string>(project.defaultBranch);
  const [buildSystem, setBuildSystem] = React.useState<BuildSystem>(project.buildSystem);
  const [paths, setPaths] = React.useState<string[]>(project.paths.split(","));


  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent
            w={"50%"}
        >
          <ModalHeader>Modify project</ModalHeader>
          <ModalCloseButton/>
          <ModalBody flexDirection="column">
            <Stack spacing={5}>
              <Input value={name} onChange={(e) => setName(e.target.value)}
                     placeholder="Project name"/>
              <Input value={url} onChange={(e) => setUrl(e.target.value)}
                     placeholder="Project url"/>
              <Input value={defaultBranch} onChange={(e) => setDefaultBranch(e.target.value)}
                     placeholder="Default branch"/>
              <FormControl>
                <FormLabel>Build system</FormLabel>
                <Select value={buildSystem}
                        onChange={(e) => setBuildSystem(e.target.value as BuildSystem)}>
                  <option value={BuildSystem.MAVEN}>Maven</option>
                  <option value={BuildSystem.GRADLE}>Gradle</option>
                </Select>
              </FormControl>
              <FormControl>
                <FormLabel>Paths</FormLabel>
              </FormControl>
              {paths.map((path, index) => (
                  <VStack
                      key={index}
                      align={"flex-end"}
                  >
                  <Textarea key={index} value={path} onChange={(e) => {
                    const newPaths = [...paths];
                    newPaths[index] = e.target.value;
                    setPaths(newPaths);
                  }} placeholder="Path"/>
                    <Button
                        variant="solid"
                        colorScheme="red"
                        onClick={() => {
                      const newPaths = [...paths];
                      newPaths.splice(index, 1);
                      setPaths(newPaths);
                    }}>Remove</Button>
                  </VStack>
              ))}
              <Button onClick={() => setPaths([...paths, ""])}>
                Add path
              </Button>
            </Stack>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" mr={3} onClick={onClose}>
              Close
            </Button>
            <Button variant="solid" bgColor="blue.400" color="white" onClick={
              () => {
                ProjectsService.updateProject(project.id, {
                  name,
                  url,
                  defaultBranch,
                  paths: paths.join(","),
                  buildSystem
                }).then(() => {
                  onClose();
                })
              }
            }>
              Save
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>)
}