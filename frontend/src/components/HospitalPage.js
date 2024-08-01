import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Form, Button, Container, Row, Col, Table } from "react-bootstrap";
import { MapContainer, TileLayer, Marker, Polyline, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

// Custom icons for start and end points
const startIcon = new L.Icon({
  iconUrl: "https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png",
  shadowUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

const endIcon = new L.Icon({
  iconUrl: "https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png",
  shadowUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

function HospitalList() {
  const [address, setAddress] = useState("");
  const [specialty, setSpecialty] = useState("");
  const [hospitals, setHospitals] = useState([]);
  const [route, setRoute] = useState([]);
  const [position, setPosition] = useState(null);
  const [selectedHospital, setSelectedHospital] = useState(null); // State to manage selected hospital
  const [routeColor, setRouteColor] = useState("blue"); // State to manage route color
  const [error, setError] = useState(""); // State to manage error messages
  const navigate = useNavigate(); // Get the navigate object for redirection

  // List of specialty options
  const specialties = [
    "Cardiology",
    "Neurology",
    "Orthopedics",
    "Gastroenterology",
  ];

  const geocodeAddress = async (address) => {
    try {
      const response = await axios.get("https://nominatim.openstreetmap.org/search", {
        params: {
          q: address,
          format: "json",
          addressdetails: 1,
          limit: 1,
        },
      });

      if (response.data.length === 0) {
        throw new Error("Address not found");
      }

      const { lat, lon } = response.data[0];
      return { lat: parseFloat(lat), lon: parseFloat(lon) };
    } catch (error) {
      console.error("Geocoding failed:", error);
      setError("Failed to geocode address");
      return null;
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      if (!address || !specialty) {
        setError("Please fill in all fields.");
        return;
      }

      const coords = await geocodeAddress(address);
      if (!coords) {
        return;
      }

      const { lat, lon } = coords;
      const response = await axios.get("http://localhost:8081/api/hospitals/nearest", {
        params: { lat, lon, specialty, limit: 5 },
      });
      const hospitalsWithInfo = await Promise.all(
        response.data.map(async (hospital) => {
          const travelInfoResponse = await axios.get("http://localhost:8081/api/hospitals/travelinfo", {
            params: {
              fromLat: lat,
              fromLon: lon,
              toLat: hospital.latitude,
              toLon: hospital.longitude,
            },
          });
          const travelInfo = travelInfoResponse.data;
          return {
            ...hospital,
            distance: travelInfo.distance,
            time: travelInfo.time,
          };
        })
      );
      setHospitals(hospitalsWithInfo);

      // Select the hospital with the shortest distance by default
      if (hospitalsWithInfo.length > 0) {
        const nearestHospital = hospitalsWithInfo.reduce((prev, curr) => (prev.distance < curr.distance ? prev : curr));
        handleSelectHospital(nearestHospital);
      }

      setPosition([lat, lon]);
    } catch (error) {
      console.error("Search failed:", error.response ? error.response.data : error.message);
      setError(error.response ? error.response.data : error.message);
    }
  };

  const handleSelectHospital = async (hospital) => {
    try {
      const coords = await geocodeAddress(address);
      if (!coords) {
        return;
      }

      const { lat, lon } = coords;
      const routeResponse = await axios.get("http://localhost:8081/api/hospitals/route", {
        params: {
          fromLat: lat,
          fromLon: lon,
          toLat: hospital.latitude,
          toLon: hospital.longitude,
        },
      });
      setRoute(routeResponse.data);
      setSelectedHospital(hospital);

      // Change route color
      const colors = ["blue"];
      const newColor = colors[Math.floor(Math.random() * colors.length)];
      setRouteColor(newColor);
    } catch (error) {
      console.error("Select hospital failed:", error.response ? error.response.data : error.message);
      setError(error.response ? error.response.data : error.message);
    }
  };

  const handleLogout = () => {
    // Perform any logout logic here if needed (e.g., clearing tokens)
    navigate("/");
  };

  return (
    <Container>
      <h1 className="my-4">Find Nearest Hospitals</h1>
      {error && <p className="text-danger">{error}</p>}
      <Form onSubmit={handleSearch}>
        <Row>
          <Col>
            <Form.Group controlId="formAddress">
              <Form.Label>Address</Form.Label>
              <Form.Control
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                placeholder="Enter address"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="formSpecialty">
              <Form.Label>Specialty</Form.Label>
              <Form.Control
                as="select"
                value={specialty}
                onChange={(e) => setSpecialty(e.target.value)}
              >
                <option value="">Select a specialty</option>
                {specialties.map((spec, index) => (
                  <option key={index} value={spec}>
                    {spec}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Col>
          <Col className="d-flex align-items-end">
            <Button variant="primary" type="submit">
              Search
            </Button>
          </Col>
          <Col className="d-flex align-items-end">
            <Button variant="danger" className="mt-3" onClick={handleLogout}>
              Logout
            </Button>
          </Col>
        </Row>
      </Form>
      {hospitals.length > 0 && (
        <Table striped bordered hover className="my-4">
          <thead>
            <tr>
              <th>#</th>
              <th>Hospital Name</th>
              <th>Specialty</th>
              <th>Distance (km)</th>
              <th>Travel Time (min)</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {hospitals.map((hospital, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{hospital.name}</td>
                <td>{hospital.specialty}</td>
                <td>{(hospital.distance / 1000).toFixed(2)}</td>
                <td>{(hospital.time / 60000).toFixed(2)}</td>
                <td>
                  <Button
                    variant="info"
                    onClick={() => handleSelectHospital(hospital)}
                  >
                    Select
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
      {position && (
        <MapContainer
          center={position}
          zoom={13}
          style={{ height: "500px", width: "100%" }}
        >
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          />
          <Marker position={position} icon={startIcon}>
            <Popup>Your Position</Popup>
          </Marker>
          {selectedHospital && (
            <Marker
              position={[selectedHospital.latitude, selectedHospital.longitude]}
              icon={endIcon}
            >
              <Popup>{selectedHospital.name}</Popup>
            </Marker>
          )}
          {route.length > 0 && <Polyline positions={route} color={routeColor} />}
        </MapContainer>
      )}
    </Container>
  );
}

export default HospitalList;
