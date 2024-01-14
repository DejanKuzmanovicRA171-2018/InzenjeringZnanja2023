import React, { useState, useEffect } from "react";

const CPU = ({ onCPUChange }) => {
  const [cpuData, setCPUData] = useState({
    cpuClockMin: 0,
    cpuClockMax: 0,
    cpuCoresMin: 0,
    cpuCoresMax: 0,
    cpuThreadsMin: 0,
    cpuThreadsMax: 0,
    socket: "",
    ramSpeedType: "",
  });
  const ramSpeedOptions = ["DDR1", "DDR2", "DDR3", "DDR4", "DDR5"];
  const handleChange = (event) => {
    const { name, value } = event.target;
    setCPUData({
      ...cpuData,
      [name]: value,
    });

    onCPUChange(cpuData);
  };
  useEffect(() => {
    onCPUChange(cpuData);
  }, [cpuData, onCPUChange]);

  return (
    <div>
      <h1>CPU</h1>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="cpuClockMin">CPU Clock Min:</label>
          <input
            type="number"
            id="cpuClockMin"
            name="cpuClockMin"
            value={cpuData.cpuClockMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="cpuClockMax">CPU Clock Max:</label>
          <input
            type="number"
            id="cpuClockMax"
            name="cpuClockMax"
            value={cpuData.cpuClockMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="cpuCoresMin">CPU Cores Min:</label>
          <input
            type="number"
            id="cpuCoresMin"
            name="cpuCoresMin"
            value={cpuData.cpuCoresMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="cpuCoresMax">CPU Cores Max:</label>
          <input
            type="number"
            id="cpuCoresMax"
            name="cpuCoresMax"
            value={cpuData.cpuCoresMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="cpuThreadsMin">CPU Threads Min:</label>
          <input
            type="number"
            id="cpuThreadsMin"
            name="cpuThreadsMin"
            value={cpuData.cpuThreadsMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="cpuThreadsMax">CPU Threads Max:</label>
          <input
            type="number"
            id="cpuThreadsMax"
            name="cpuThreadsMax"
            value={cpuData.cpuThreadsMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="socket">Socket:</label>
          <input
            type="text"
            id="socket"
            name="socket"
            value={cpuData.socket}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="ramSpeedType">Compatible with RAM type:</label>
          <select
            id="ramSpeedType"
            name="ramSpeedType"
            value={cpuData.ramSpeedType}
            onChange={handleChange}
          >
            <option value="">Select RAM Speed Type</option>
            {ramSpeedOptions.map((ramSpeed) => (
              <option key={ramSpeed} value={ramSpeed}>
                {ramSpeed}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
};

export default CPU;
