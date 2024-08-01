import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Alert } from "react-bootstrap";

function RegisterPage() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("user");
  const roleSend = ["user"];
  const [error, setError] = useState(""); // State to manage error messages
  const navigate = useNavigate(); // Get the navigate object for redirection

  const handleSignup = async () => {
    try {
      // Check for empty fields
      if (!email || !password || !confirmPassword) {
        setError("Please fill in all fields.");
        return;
      }

      if (password !== confirmPassword) {
        throw new Error("Passwords do not match");
      }

      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        {
          username,
          email,
          password,
          roleSend,
        }
      );
      // Handle successful signup
      console.log(response.data);
      navigate("/hospitals");
    } catch (error) {
      // Handle signup error
      console.error(
        "Signup failed:",
        error.response ? error.response.data : error.message
      );
      setError(error.response ? error.response.data : error.message);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div
        className="border rounded-lg p-4"
        style={{ width: "600px", height: "auto" }}
      >
        <Container className="p-3">
          <h2 className="mb-4 text-center">Register Page</h2>
          {/* Render error message if exists */}
          {error && <Alert variant="danger">{error}</Alert>}

          <Form>
            <Form.Group controlId="username" className="mb-3">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="email" className="mb-3">
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="password" className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Enter password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="confirmPassword" className="mb-3">
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Confirm password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="role" className="mb-3">
              <Form.Label>Role</Form.Label>
              <Form.Control
                as="select"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              >
                <option value="user">User</option>
                <option value="mod">Moderator</option>
                <option value="admin">Admin</option>
              </Form.Control>
            </Form.Group>

            <Button variant="primary" className="w-100" onClick={handleSignup}>
              Sign Up
            </Button>
          </Form>

          <div className="text-center mt-3">
            <p>
              Already registered? <a href="/">Login</a>
            </p>
          </div>
        </Container>
      </div>
    </div>
  );
}

export default RegisterPage;
