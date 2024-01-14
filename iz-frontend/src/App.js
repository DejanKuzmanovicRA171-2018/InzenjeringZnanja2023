import "./App.css";
import React, { Component } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import HomePage from "./components/homePage";
import Components from "./components/components";
import Computers from "./components/computers";
import Cooling from "./components/pcComponents/cooling";
import GPU from "./components/pcComponents/gpu";
import CPU from "./components/pcComponents/cpu";
import RAM from "./components/pcComponents/ram";
import Storage from "./components/pcComponents/storage";

import NavBar from "./components/navBar";

class App extends Component {
  render() {
    return (
      <React.Fragment>
        <main className="container">
          <Router>
            <NavBar />
            <Routes>
              <Route path="/homePage" element={<HomePage />} />
              <Route path="/components" element={<Components />} />
              <Route path="/computers" element={<Computers />} />
              <Route path="/cooling" element={<Cooling />} />
              <Route path="/gpu" element={<GPU />} />
              <Route path="/cpu" element={<CPU />} />
              <Route path="/ram" element={<RAM />} />
              <Route path="/storage" element={<Storage />} />
              <Route path="/" element={<Navigate to="/homePage" replace />} />
              <Route path="*" element={<Navigate to="/not-found" replace />} />
            </Routes>
          </Router>
        </main>
      </React.Fragment>
    );
  }
}

export default App;
