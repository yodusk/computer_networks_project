import {useState} from 'react'
import {HashRouter, Route, Routes} from "react-router-dom";
import {ProjectsPage} from "./pages/ProjectsPage";
import {ProjectPage} from "./pages/ProjectPage";
import {ChakraProvider} from "@chakra-ui/react";

function App() {
  const [count, setCount] = useState(0)

  return (
      <ChakraProvider>
      <HashRouter>
        <Routes>
          <Route path="/projects" element={<ProjectsPage/>}/>
          <Route path="/projects/:id" element={<ProjectPage />}/>
          <Route path="/*" element={<ProjectsPage/>}/>
        </Routes>
      </HashRouter>
      </ChakraProvider>
  )
}

export default App
