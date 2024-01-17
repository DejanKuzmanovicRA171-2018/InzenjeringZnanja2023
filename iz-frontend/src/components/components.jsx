import React, { useState, useEffect } from "react";
import CPU from "./pcComponents/cpu";
import GPU from "./pcComponents/gpu";
import RAM from "./pcComponents/ram";
import Storage from "./pcComponents/storage";
import Cooling from "./pcComponents/cooling";
import PowerSupply from "./pcComponents/powerSupply";
import Motherboard from "./pcComponents/motherboard";
import axios from "axios";

const Components = () => {
  const [ramData, setRamData] = useState({});
  const [cpuData, setCPUData] = useState({});
  const [gpuData, setGPUData] = useState({});
  const [storageData, setStorageData] = useState({});
  const [coolingData, setCoolingData] = useState({});
  const [powerSupplyData, setPowerSupplyData] = useState({});
  const [motherboardData, setMotherboardData] = useState({});
  const [recComponents, setRecComponents] = useState(null);
  const [gamingScore, setGamingScore] = useState(null);
  const [loading, setLoading] = useState(false);
  const labels = [
    "Processor: ",
    "GPU: ",
    "RAM: ",
    "Motherboard: ",
    "Storage: ",
    "Power supply: ",
    "Case: ",
    "Cooling: ",
  ];
  const postData = async () => {
    setLoading(true);
    setGamingScore(null);
    const url = "http://localhost:8080/recommend";

    const requestBody = {
      cpuClockMin: cpuData.cpuClockMin || 0,
      cpuClockMax: cpuData.cpuClockMax || 0,
      cpuCoresMin: cpuData.cpuCoresMin || 0,
      cpuCoresMax: cpuData.cpuCoresMax || 0,
      cpuThreadsMin: cpuData.cpuThreadsMin || 0,
      cpuThreadsMax: cpuData.cpuThreadsMax || 0,
      cpuRamSpeedType: cpuData.ramSpeedType,
      cpuSocket: cpuData.socket,
      gpuClockMin: gpuData.gpuClockMin || 0,
      gpuClockMax: gpuData.gpuClockMax || 0,
      gpuVRAMMin: gpuData.gpuVRAMMin || 0,
      gpuVRAMMax: gpuData.gpuVRAMMax || 0,
      pciEGPU: gpuData.pciE,
      ramClockMin: ramData.ramClockMin || 0,
      ramClockMax: ramData.ramClockMax || 0,
      ramSizeMin: ramData.ramSizeMin || 0,
      ramSizeMax: ramData.ramSizeMax || 0,
      ramSpeedType: ramData.ramSpeedType,
      storageWriteSpeedMin: storageData.storageWriteSpeedMin || 0,
      storageWriteSpeedMax: storageData.storageWriteSpeedMax || 0,
      storageCapacityMin: storageData.storageCapacityMin || 0,
      storageCapacityMax: storageData.storageCapacityMax || 0,
      storageType: storageData.driveType,
      storageRPM: storageData.rpm || 0,
      minimalThermalPerformance: coolingData.minThermalPerformance || 0,
      coolingSockets: coolingData.socket,
      minPSUPower: powerSupplyData.minPower || 0,
      maxPSUPower: powerSupplyData.maxPower || 0,
      minNumOfRamSlotsMb: motherboardData.minNumberOfRamSlots || 0,
      maxNumOfRamSlotsMb: motherboardData.maxNumberOfRamSlots || 0,
      minRamCapacityMb: motherboardData.minRamCapacity || 0,
      maxRamCapacityMb: motherboardData.maxRamCapacity || 0,
      mbSocket: motherboardData.socket,
      pciEMb: motherboardData.pciE,
    };
    console.log("CAOS", requestBody);
    try {
      setRecComponents(null);

      const response = await axios.post(url, requestBody);
      console.log("POST request successful:", response.data);
      if (response.data === "") {
        alert("No pc configuration matches provided requirements");
      }
      setGamingScore(response.data.usageScores[0]);

      setRecComponents(response.data.recommendedComponents);
    } catch (error) {
      console.error("Error making POST request:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleRamChange = (newData) => {
    setRamData(newData);
  };

  const handleCPUChange = (newData) => {
    setCPUData(newData);
  };

  const handleGPUChange = (newData) => {
    setGPUData(newData);
  };

  const handleStorageChange = (newData) => {
    setStorageData(newData);
  };

  const handleCoolingChange = (newData) => {
    setCoolingData(newData);
  };

  const handlePowerSupplyChange = (newData) => {
    setPowerSupplyData(newData);
  };

  const handleMotherboardChange = (newData) => {
    setMotherboardData(newData);
  };

  const componentStyle = {
    border: "1px solid black",
    padding: "10px",
    margin: "5px",
  };

  return (
    <>
      <h1>Components</h1>
      <div style={{ display: "flex", justifyContent: "space-around" }}>
        <div style={componentStyle}>
          <CPU onCPUChange={handleCPUChange} />
        </div>
        <div style={componentStyle}>
          <GPU onGPUChange={handleGPUChange} />
        </div>
        <div style={componentStyle}>
          <Motherboard onMotherboardChange={handleMotherboardChange} />
        </div>
        <div style={componentStyle}>
          <RAM onRamChange={handleRamChange} />
        </div>

        <div style={componentStyle}>
          <Storage onStorageChange={handleStorageChange} />
        </div>
        <div style={componentStyle}>
          <Cooling onCoolingChange={handleCoolingChange} />
        </div>
        <div style={componentStyle}>
          <PowerSupply onPowerSupplyChange={handlePowerSupplyChange} />
        </div>
      </div>

      <button onClick={postData}>Send POST Request</button>
      {loading ? <p>Loading...</p> : null}
      {recComponents && (
        <div>
          <h2>Recommended Components:</h2>
          {recComponents.split(" || ").map((result, index) => (
            <p key={index}>
              <strong>{labels[index]}</strong>
              {result}
            </p>
          ))}
        </div>
      )}
      {gamingScore && <p>Gaming score: {gamingScore.toFixed(2)}%</p>}
    </>
  );
};

export default Components;
