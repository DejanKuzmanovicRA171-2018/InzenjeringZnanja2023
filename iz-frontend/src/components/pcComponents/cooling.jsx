import React, { useState, useEffect } from "react";

const Cooling = ({ onCoolingChange }) => {
  const [coolingData, setCoolingData] = useState({
    minThermalPerformance: 0,
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCoolingData({
      ...coolingData,
      [name]: value,
    });

    onCoolingChange(coolingData);
  };
  useEffect(() => {
    onCoolingChange(coolingData);
  }, [coolingData, onCoolingChange]);

  return (
    <div>
      <h1>Cooling</h1>
      <div>
        <label>Minimum Thermal Performance: </label>
        <input
          type="number"
          value={coolingData.minThermalPerformance}
          onChange={handleChange}
        />
      </div>
    </div>
  );
};

export default Cooling;
