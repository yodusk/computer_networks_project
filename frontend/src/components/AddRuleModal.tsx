import {
  Button,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Stack,
  Textarea
} from "@chakra-ui/react";
import React from "react";
import {CreateRuleDto, ResponseRuleDto} from "../types/Rule";
import RuleService from "../services/RuleService";

interface AddProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
  onOpen: () => void;
  projectId: number;

  rules: ResponseRuleDto[];
  setRules: React.Dispatch<React.SetStateAction<ResponseRuleDto[]>>;
}

export const AddRuleModal: React.FC<AddProjectModalProps> = ({
                                                               isOpen,
                                                               onClose,
                                                               onOpen,
                                                               projectId,
                                                               rules,
                                                               setRules
                                                             }) => {
  const [rule, setRule] = React.useState<CreateRuleDto>({
    id: 0,
    name: "",
    query: "",
    description: "",
    projectId: projectId
  });

  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          <ModalHeader>Create project</ModalHeader>
          <ModalCloseButton/>
          <ModalBody flexDirection="column">
            <Stack spacing={5}>
              <Input value={rule.name} onChange={(e) => setRule({...rule, name: e.target.value})}
                     placeholder="Rule name"/>
              <Textarea value={rule.query}
                        onChange={(e) => setRule({...rule, query: e.target.value})}
                        placeholder="Rule query"/>
              <Textarea value={rule.description}
                        onChange={(e) => setRule({...rule, description: e.target.value})}
                        placeholder="Rule description"/>
            </Stack>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" mr={3} onClick={onClose}>
              Close
            </Button>
            <Button variant="solid" bgColor="blue.400" color="white"
                    onClick={() => {
                      RuleService.createRule(rule).then(() => {
                        setRules([...rules, rule]);
                        window.location.reload()
                        onClose();
                      })
                    }}
            >
              Create
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>)
}