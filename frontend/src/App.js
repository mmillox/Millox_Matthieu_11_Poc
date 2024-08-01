import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
// import DashboardPage from "./components/DashboardPage.js";
import HospitalPage from "./components/HospitalPage.js";
import { Container } from "react-bootstrap";

function App() {
  return (
    <div className="App">
      <Router>
        <Container>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/signup" element={<RegisterPage />} />
            {/* <Route path="/dashboard" element={<DashboardPage />} /> */}
            <Route path="/hospitals" element={<HospitalPage />} />
          </Routes>
        </Container>
      </Router>
    </div>
  );
}

export default App;
