import {
  Button,
  FormControl,
  FormLabel,
  Icon,
  Input,
  InputGroup,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Stack
} from "@chakra-ui/react";
import React from "react";

interface AddProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
  onOpen: () => void;
}

export const AddRuleModal: React.FC<AddProjectModalProps> = ({isOpen, onClose, onOpen}) => {
  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          <ModalHeader>Create project</ModalHeader>
          <ModalCloseButton/>
          <ModalBody flexDirection="column">
            <Stack spacing={5}>

            </Stack>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" mr={3} onClick={onClose}>
              Close
            </Button>
            <Button variant="solid" bgColor="blue.400" color="white">
              Create
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>)
}