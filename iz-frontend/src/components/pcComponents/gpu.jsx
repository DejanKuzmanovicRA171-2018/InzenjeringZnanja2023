import React, { useState, useEffect } from "react";
import Slider from "react-slider";

const GPU = ({ onGPUChange }) => {
  const [gpuData, setGPUData] = useState({
    gpuClockMin: 0,
    gpuClockMax: 7000,
    gpuVRAMMin: 0,
    gpuVRAMMax: 0,
    pciE: [],
  });

  const pciEOptions = [
    "1.0x1",
    "2.0x1",
    "2.0x4",
    "2.0x16",
    "3.0x1",
    "3.0x4",
    "3.0x8",
    "3.0x16",
    "4.0x16",
    "PCI-5V",
  ];

  const handleSliderChange = (values) => {
    setGPUData((prevData) => ({
      ...prevData,
      gpuClockMin: values[0],
      gpuClockMax: values[1],
    }));
  };

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;

    // Handle checkboxes separately
    if (type === "checkbox") {
      setGPUData((prevData) => ({
        ...prevData,
        [name]: checked
          ? [...prevData[name], value]
          : prevData[name].filter((v) => v !== value),
      }));
    } else {
      setGPUData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  useEffect(() => {
    onGPUChange(gpuData);
  }, [gpuData, onGPUChange]);

  return (
    <div>
      <h1>GPU</h1>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="gpuClockMin">GPU Clock Min:</label>
          <input
            type="number"
            id="gpuClockMin"
            name="gpuClockMin"
            value={gpuData.gpuClockMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuClockMax">GPU Clock Max:</label>
          <input
            type="number"
            id="gpuClockMax"
            name="gpuClockMax"
            value={gpuData.gpuClockMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuVRAMMin">GPU VRAM Min:</label>
          <input
            type="number"
            id="gpuVRAMMin"
            name="gpuVRAMMin"
            value={gpuData.gpuVRAMMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuVRAMMax">GPU VRAM Max:</label>
          <input
            type="number"
            id="gpuVRAMMax"
            name="gpuVRAMMax"
            value={gpuData.gpuVRAMMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="pciE">Has PCI-E:</label>
          {pciEOptions.map((option) => (
            <div key={option}>
              <input
                type="checkbox"
                id={option}
                name="pciE"
                value={option}
                checked={gpuData.pciE.includes(option)}
                onChange={handleChange}
              />
              <label htmlFor={option}>{option}</label>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default GPU;
