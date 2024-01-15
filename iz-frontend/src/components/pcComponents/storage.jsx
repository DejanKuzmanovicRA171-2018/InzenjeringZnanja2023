import React, { useState, useEffect } from "react";

const Storage = ({ onStorageChange }) => {
  const [storageData, setStorageData] = useState({
    storageWriteSpeedMin: 0,
    storageWriteSpeedMax: 0,
    storageCapacityMin: 0,
    storageCapacityMax: 0,
    driveType: "SSD",
    rpm: 0,
  });

  const handleChange = (event) => {
    const { name, value, type } = event.target;

    if (type === "radio") {
      const newDriveType = value === "HDD" ? "HDD" : "SSD";
      setStorageData({
        ...storageData,
        driveType: newDriveType,
        rpm: newDriveType === "HDD" ? storageData.rpm : 0,
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
          <label htmlFor="storageWriteSpeedMin">
            Storage Write Speed Min (MB/s):
          </label>
          <input
            type="number"
            id="storageWriteSpeedMin"
            name="storageWriteSpeedMin"
            value={storageData.storageWriteSpeedMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageWriteSpeedMax">
            Storage Write Speed Max (MB/s):
          </label>
          <input
            type="number"
            id="storageWriteSpeedMax"
            name="storageWriteSpeedMax"
            value={storageData.storageWriteSpeedMax}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageCapacityMin">Storage Capacity Min (GB):</label>
          <input
            type="number"
            id="storageCapacityMin"
            name="storageCapacityMin"
            value={storageData.storageCapacityMin}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="storageCapacityMax">Storage Capacity Max (GB):</label>
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

        {storageData.driveType === "HDD" && (
          <div>
            <label htmlFor="rpm">RPM: </label>
            <input
              type="number"
              id="rpm"
              name="rpm"
              value={storageData.rpm}
              onChange={handleChange}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default Storage;
