import React, { useState, useEffect } from "react";

const RAM = ({ onRamChange }) => {
  const [ramData, setRamData] = useState({
    ramClockMin: 0,
    ramClockMax: 0,
    ramSizeMin: 0,
    ramSizeMax: 0,
    numberOfRamSlots: 0,
    ramSpeedType: "",
  });
  const ramSpeedOptions = ["DDR1", "DDR2", "DDR3", "DDR4", "DDR5"];
  const handleChange = (event) => {
    const { name, value } = event.target;
    setRamData({
      ...ramData,
      [name]: value,
    });

    onRamChange(ramData);
  };
  useEffect(() => {
    onRamChange(ramData);
  }, [ramData, onRamChange]);

  return (
    <div>
      <h1>RAM</h1>
      <div>
        <label htmlFor="ramSpeedType">RAM Speed Type:</label>
        <select
          id="ramSpeedType"
          name="ramSpeedType"
          value={ramData.ramSpeedType}
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
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="ramClockMin">RAM Clock Min (MHz):</label>
          <input
            type="number"
            id="ramClockMin"
            name="ramClockMin"
            value={ramData.ramClockMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="ramClockMax">RAM Clock Max (MHz):</label>
          <input
            type="number"
            id="ramClockMax"
            name="ramClockMax"
            value={ramData.ramClockMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="ramSizeMin">RAM Size Min (GB):</label>
          <input
            type="number"
            id="ramSizeMin"
            name="ramSizeMin"
            value={ramData.ramSizeMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="ramSizeMax">RAM Size Max (GB):</label>
          <input
            type="number"
            id="ramSizeMax"
            name="ramSizeMax"
            value={ramData.ramSizeMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="numberOfRamSlots">Number Of Ram Slots:</label>
          <input
            type="number"
            id="numberOfRamSlots"
            name="numberOfRamSlots"
            value={ramData.numberOfRamSlots}
            onChange={handleChange}
          />
        </div>
      </div>
    </div>
  );
};

export default RAM;
