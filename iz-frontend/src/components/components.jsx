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

  const postData = async () => {
    const url = "your_api_endpoint"; // Replace with your actual API endpoint

    const requestBody = {
      ramSlots: 4,

      cpuClockMin: cpuData.cpuClockMin,
      cpuClockMax: cpuData.cpuClockMax,
      cpuCoresMin: cpuData.cpuCoresMin,
      cpuCoresMax: cpuData.cpuCoresMax,
      cpuThreadsMin: cpuData.cpuThreadsMin,
      cpuThreadsMax: cpuData.cpuThreadsMax,
      cpuRamSpeedType: cpuData.ramSpeedType,
      gpuClockMin: gpuData.gpuClockMin,
      gpuClockMax: gpuData.gpuClockMax,
      gpuVRAMMin: gpuData.gpuVRAMMin,
      gpuVRAMMax: gpuData.gpuVRAMMax,
      pciEGPU: gpuData.pciE,
      ramClockMin: ramData.ramClockMin,
      ramClockMax: ramData.ramClockMax,
      ramSizeMin: ramData.ramSizeMin,
      ramSizeMax: ramData.ramSizeMax,
      ramSpeedType: ramData.ramSpeedType,
      storageWriteSpeedMin: storageData.storageWriteSpeedMin,
      storageWriteSpeedMax: storageData.storageWriteSpeedMax,
      storageCapacityMin: storageData.storageCapacityMin,
      storageCapacityMax: storageData.storageCapacityMax,
      storageType: storageData.storageType,
      storageRPM: 0,
      minimalThermalPerformance: coolingData.minThermalPerformance,
      minPSUPower: powerSupplyData.minPower,
      maxPSUPower: powerSupplyData.maxPower,
      minNumOfRamSlotsMb: motherboardData.minNumberOfRamSlots,
      maxNumOfRamSlotsMb: motherboardData.maxNumberOfRamSlots,
      minRamCapacityMb: motherboardData.minRamCapacity,
      maxRamCapacityMb: motherboardData.maxRamCapacity,
      pciEMb: motherboardData.pciE,
    };
    console.log("CAOS", requestBody);
    try {
      const response = await axios.post(url, requestBody);
      console.log("POST request successful:", response.data);
    } catch (error) {
      console.error("Error making POST request:", error);
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
    </>
  );
};

export default Components;
