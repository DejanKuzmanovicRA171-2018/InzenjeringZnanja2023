import React, { useState, useEffect } from "react";

const PowerSupply = ({ onPowerSupplyChange }) => {
  const [powerSupplyData, setPowerSupplyData] = useState({
    minPower: 0,
    maxPower: 0,
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPowerSupplyData({
      ...powerSupplyData,
      [name]: value,
    });

    onPowerSupplyChange(powerSupplyData);
  };
  useEffect(() => {
    onPowerSupplyChange(powerSupplyData);
  }, [powerSupplyData, onPowerSupplyChange]);

  return (
    <div>
      <h1>Power Supply</h1>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="minPower">Min Power (Watts):</label>
          <input
            type="number"
            id="minPower"
            name="minPower"
            value={powerSupplyData.minPower}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="maxPower">Max Power (Watts):</label>
          <input
            type="number"
            id="maxPower"
            name="maxPower"
            value={powerSupplyData.maxPower}
            onChange={handleChange}
          />
        </div>
      </div>
    </div>
  );
};

export default PowerSupply;
