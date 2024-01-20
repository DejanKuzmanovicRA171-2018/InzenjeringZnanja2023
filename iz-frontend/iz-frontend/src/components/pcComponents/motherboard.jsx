import React, { useState, useEffect } from "react";

const Motherboard = ({ onMotherboardChange }) => {
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

  const ramSpeedOptions = ["DDR1", "DDR2", "DDR3", "DDR4", "DDR5"];

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

  const [motherboardData, setMotherboardData] = useState({
    minNumberOfRamSlots: 0,
    maxNumberOfRamSlots: 0,
    minRamCapacity: 0,
    maxRamCapacity: 0,
    pciE: [],
    socket: "",
    ramSpeedType: "",
  });

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;

    if (type === "checkbox") {
      setMotherboardData((prevData) => ({
        ...prevData,
        [name]: checked
          ? [...prevData[name], value]
          : prevData[name].filter((v) => v !== value),
      }));
    } else {
      setMotherboardData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  useEffect(() => {
    onMotherboardChange(motherboardData);
  }, [motherboardData, onMotherboardChange]);

  return (
    <div>
      <h1>Motherboard</h1>
      <div>
        <label htmlFor="minNumberOfRamSlots">Min Number of RAM Slots:</label>
        <input
          type="number"
          id="minNumberOfRamSlots"
          name="minNumberOfRamSlots"
          value={motherboardData.minNumberOfRamSlots}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="maxNumberOfRamSlots">Max Number of RAM Slots:</label>
        <input
          type="number"
          id="maxNumberOfRamSlots"
          name="maxNumberOfRamSlots"
          value={motherboardData.maxNumberOfRamSlots}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="minRamCapacity">Min RAM Capacity (GB):</label>
        <input
          type="number"
          id="minRamCapacity"
          name="minRamCapacity"
          value={motherboardData.minRamCapacity}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="maxRamCapacity">Max RAM Capacity (GB):</label>
        <input
          type="number"
          id="maxRamCapacity"
          name="maxRamCapacity"
          value={motherboardData.maxRamCapacity}
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
              checked={motherboardData.pciE.includes(option)}
              onChange={handleChange}
            />
            <label htmlFor={option}>{option}</label>
          </div>
        ))}
      </div>
      <div>
        <label htmlFor="socket">Socket: </label>
        <select
          id="socket"
          name="socket"
          value={motherboardData.socket}
          onChange={handleChange}
        >
          <option value="">Select Socket</option>
          {socketOptions.map((socket) => (
            <option key={socket} value={socket}>
              {socket}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label htmlFor="ramSpeedType">Compatible with RAM type:</label>
        <select
          id="ramSpeedType"
          name="ramSpeedType"
          value={motherboardData.ramSpeedType}
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
  );
};

export default Motherboard;
