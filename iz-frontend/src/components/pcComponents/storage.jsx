import React, { useState, useEffect } from "react";

const Storage = ({ onStorageChange }) => {
  const [storageData, setStorageData] = useState({
    storageWriteSpeedMin: 0,
    storageWriteSpeedMax: 0,
    storageCapacityMin: 0,
    storageCapacityMax: 0,
    driveType: "SSD", // Default value
  });

  const handleChange = (event) => {
    const { name, value, type } = event.target;

    // Handle radio button separately
    if (type === "radio") {
      setStorageData({
        ...storageData,
        [name]: value,
      });
    } else {
      setStorageData({
        ...storageData,
        [name]: value,
      });
    }

    onStorageChange(storageData);
  };

  useEffect(() => {
    onStorageChange(storageData);
  }, [storageData, onStorageChange]);

  return (
    <div>
      <h1>Storage</h1>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div>
          <label htmlFor="storageWriteSpeedMin">Storage Write Speed Min:</label>
          <input
            type="number"
            id="storageWriteSpeedMin"
            name="storageWriteSpeedMin"
            value={storageData.storageWriteSpeedMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageWriteSpeedMax">Storage Write Speed Max:</label>
          <input
            type="number"
            id="storageWriteSpeedMax"
            name="storageWriteSpeedMax"
            value={storageData.storageWriteSpeedMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageCapacityMin">Storage Capacity Min:</label>
          <input
            type="number"
            id="storageCapacityMin"
            name="storageCapacityMin"
            value={storageData.storageCapacityMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageCapacityMax">Storage Capacity Max:</label>
          <input
            type="number"
            id="storageCapacityMax"
            name="storageCapacityMax"
            value={storageData.storageCapacityMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Drive Type:</label>
          <div>
            <input
              type="radio"
              id="ssd"
              name="driveType"
              value="SSD"
              checked={storageData.driveType === "SSD"}
              onChange={handleChange}
            />
            <label htmlFor="ssd">SSD</label>
          </div>
          <div>
            <input
              type="radio"
              id="hdd"
              name="driveType"
              value="HDD"
              checked={storageData.driveType === "HDD"}
              onChange={handleChange}
            />
            <label htmlFor="hdd">HDD</label>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Storage;
