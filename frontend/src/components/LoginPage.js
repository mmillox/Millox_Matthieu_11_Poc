import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Alert } from "react-bootstrap";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      if (!username || !password) {
        setError("Please enter both username and password.");
        return;
      }

      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        {
          username,
          password,
        }
      );
      console.log("Login successful:", response.data);
      console.log("Username : ", username);
      navigate("/hospitals", { state: { username } });
    } catch (error) {
      console.error(
        "Login failed:",
        error.response ? error.response.data : error.message
      );
      setError("Invalid username or password.");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div
        className="border rounded-lg p-4"
        style={{ width: "500px", height: "auto" }}
      >
        <Container className="p-3">
          <h2 className="mb-4 text-center">Login Page</h2>
          <Form>
            <Form.Group controlId="username">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="password" className="mt-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Enter password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>

            {error && (
              <Alert variant="danger" className="mt-3">
                {error}
              </Alert>
            )}

            <Button
              variant="primary"
              className="mt-3 w-100"
              onClick={handleLogin}
            >
              Sign In
            </Button>
          </Form>
          <div className="text-center mt-2">
            <p>
              Not a member? <a href="/signup">Register</a>
            </p>
          </div>
        </Container>
      </div>
    </div>
  );
}

export default LoginPage;
