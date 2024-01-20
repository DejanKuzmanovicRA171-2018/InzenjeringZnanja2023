import React, { useState, useEffect } from "react";

const Cooling = ({ onCoolingChange }) => {
  const socketOptions = [
    "AM1",
    "AM2",
    "AM2+",
    "AM3",
    "AM3+",
    "AM4",
    "AM5",
    "Intel 1150",
    "Intel 1151",
    "Intel 1155",
    "Intel 1156",
    "Intel 1168",
    "Intel 1200",
    "Intel 1366",
    "Intel 1700",
    "Intel 2011-3",
    "Intel 2066",
    "Intel 4677",
    "LGA 775",
    "SP5",
    "sTR5",
    "TR4",
  ];

  const [coolingData, setCoolingData] = useState({
    minThermalPerformance: 0,
    socket: [],
  });

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;

    if (checked) {
      setCoolingData({
        ...coolingData,
        socket: [...coolingData.socket, name],
      });
    } else {
      setCoolingData({
        ...coolingData,
        socket: coolingData.socket.filter((socket) => socket !== name),
      });
    }
  };

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
          name="minThermalPerformance"
          value={coolingData.minThermalPerformance}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Socket Options: </label>
        {socketOptions.map((socket) => (
          <div key={socket}>
            <input
              type="checkbox"
              name={socket}
              checked={coolingData.socket.includes(socket)}
              onChange={handleCheckboxChange}
            />
            <label>{socket}</label>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Cooling;
