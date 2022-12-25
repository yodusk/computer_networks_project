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
  rule: ResponseRuleDto
  setRule: (rule: ResponseRuleDto) => void;
}

export const ModifyRuleModal: React.FC<AddProjectModalProps> = ({
                                                                  isOpen,
                                                                  onClose,
                                                                  onOpen,
                                                                  rule,
                                                                }) => {
  const [modifiedRule, setModifiedRule] = React.useState<CreateRuleDto>({
    id: rule.id as number,
    name: rule.name,
    query: rule.query,
    description: rule.description,
    projectId: rule.projectId
  });
  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          <ModalHeader>Create project</ModalHeader>
          <ModalCloseButton/>
          <ModalBody flexDirection="column">
            <Stack spacing={5}>
              <Stack spacing={5}>
                <Input value={modifiedRule.name}
                       onChange={(e) => setModifiedRule({...modifiedRule, name: e.target.value})}
                       placeholder="Rule name"/>
                <Textarea value={modifiedRule.query}
                          onChange={(e) => setModifiedRule({...modifiedRule, query: e.target.value})}
                          placeholder="Rule query"/>
                <Textarea value={modifiedRule.description}
                          onChange={(e) => setModifiedRule({...modifiedRule, description: e.target.value})}
                          placeholder="Rule description"/>
              </Stack>

            </Stack>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" mr={3} onClick={onClose}>
              Close
            </Button>
            <Button variant="solid" bgColor="blue.400" color="white"
                    onClick={() => {
                      RuleService.updateRule(rule.id as number, modifiedRule).then(() => {
                        onClose();
                      })
                    }}
            >
              Update
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>)
}