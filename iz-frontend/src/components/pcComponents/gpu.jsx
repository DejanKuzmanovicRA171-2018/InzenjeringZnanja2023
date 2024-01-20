import React, { useState, useEffect } from "react";

const GPU = ({ onGPUChange }) => {
  const [gpuData, setGPUData] = useState({
    gpuClockMin: 0,
    gpuClockMax: 0,
    gpuVRAMMin: 0,
    gpuVRAMMax: 0,
    pciE: "",
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
    "5.0x16",
    "PCI-5V",
  ];

  const handleChange = (event) => {
    const { name, value } = event.target;

    setGPUData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  useEffect(() => {
    onGPUChange(gpuData);
  }, [gpuData, onGPUChange]);

  return (
    <div>
      <h1>GPU</h1>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="gpuClockMin">GPU Clock Min (MHz):</label>
          <input
            type="number"
            id="gpuClockMin"
            name="gpuClockMin"
            value={gpuData.gpuClockMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuClockMax">GPU Clock Max (MHz):</label>
          <input
            type="number"
            id="gpuClockMax"
            name="gpuClockMax"
            value={gpuData.gpuClockMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuVRAMMin">GPU VRAM Min (Mb):</label>
          <input
            type="number"
            id="gpuVRAMMin"
            name="gpuVRAMMin"
            value={gpuData.gpuVRAMMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="gpuVRAMMax">GPU VRAM Max (Mb):</label>
          <input
            type="number"
            id="gpuVRAMMax"
            name="gpuVRAMMax"
            value={gpuData.gpuVRAMMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="pciE">PCI-E: </label>
          <select
            id="pciE"
            name="pciE"
            value={gpuData.pciE}
            onChange={handleChange}
          >
            <option value="">Select PCI-E</option>
            {pciEOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
};

export default GPU;
