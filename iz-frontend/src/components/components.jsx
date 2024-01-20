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
  const [hostingScore, setHostingScore] = useState(null);
  const [homeScore, setHomeScore] = useState(null);
  const [workScore, setWorkScore] = useState(null);
  const [miningScore, setMiningScore] = useState(null);
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
    setHostingScore(null);
    setHomeScore(null);
    setWorkScore(null);
    setMiningScore(null);
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
    try {
      setRecComponents(null);

      const response = await axios.post(url, requestBody);
      console.log("POST request successful:", response.data);
      if (response.data.recommendedComponents === null) {
        alert("No pc configuration matches provided requirements");
      }
      setGamingScore(response.data.usageScores[0]);
      setHostingScore(response.data.usageScores[1]);
      setHomeScore(response.data.usageScores[2]);
      setWorkScore(response.data.usageScores[3]);
      setMiningScore(response.data.usageScores[4]);

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
  const getColorizedScore = (score) => {
    let color = "";

    if (score <= 33) {
      color = "red";
    } else if (score <= 66) {
      color = "orange";
    } else {
      color = "green";
    }

    return <span style={{ color }}>{score.toFixed(2)}%</span>;
  };

  return (
    <>
      <h1>Components</h1>
      <div style={{ display: "flex", justifyContent: "space-around" }}>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <CPU onCPUChange={handleCPUChange} />
        </div>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <GPU onGPUChange={handleGPUChange} />
        </div>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <Motherboard onMotherboardChange={handleMotherboardChange} />
        </div>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <RAM onRamChange={handleRamChange} />
        </div>

        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <Storage onStorageChange={handleStorageChange} />
        </div>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <Cooling onCoolingChange={handleCoolingChange} />
        </div>
        <div style={{ ...componentStyle, border: "1px solid black" }}>
          <PowerSupply onPowerSupplyChange={handlePowerSupplyChange} />
        </div>
      </div>

      <button onClick={postData}>Send POST Request</button>
      {loading ? <p>Loading...</p> : null}
      {recComponents && (
        <div
          style={{ border: "1px solid black", padding: "10px", margin: "5px" }}
        >
          <h2>Recommended Components:</h2>
          {recComponents.split(" || ").map((result, index) => (
            <p key={index}>
              <strong>{labels[index]}</strong>
              {result}
            </p>
          ))}
        </div>
      )}
      {gamingScore !== null && gamingScore !== 0 && (
        <div
          style={{
            border: "1px solid black",
            width: "10%",
            padding: "10px",
            margin: "5px",
          }}
        >
          <p>Gaming score: {getColorizedScore(gamingScore)}</p>
        </div>
      )}
      {hostingScore !== null && hostingScore !== 0 && (
        <div
          style={{
            border: "1px solid black",
            width: "10%",
            padding: "10px",
            margin: "5px",
          }}
        >
          <p>Hosting score: {getColorizedScore(hostingScore)}</p>
        </div>
      )}
      {homeScore !== null && homeScore !== 0 && (
        <div
          style={{
            border: "1px solid black",
            width: "10%",
            padding: "10px",
            margin: "5px",
          }}
        >
          <p>Home score: {getColorizedScore(homeScore)}</p>
        </div>
      )}
      {workScore !== null && workScore !== 0 && (
        <div
          style={{
            border: "1px solid black",
            width: "10%",
            padding: "10px",
            margin: "5px",
          }}
        >
          <p>Work score: {getColorizedScore(workScore)}</p>
        </div>
      )}
      {miningScore !== null && miningScore !== 0 && (
        <div
          style={{
            border: "1px solid black",
            width: "10%",
            padding: "10px",
            margin: "5px",
          }}
        >
          <p>Mining score: {getColorizedScore(miningScore)}</p>
        </div>
      )}
    </>
  );
};

export default Components;
