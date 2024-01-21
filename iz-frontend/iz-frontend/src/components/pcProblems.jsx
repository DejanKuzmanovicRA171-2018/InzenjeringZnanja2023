import React, { useState } from "react";
import axios from "axios";

const PCProblems = () => {
  const [checkedItems, setCheckedItems] = useState({});
  const [probability, setProbability] = useState({});

  const handleChange = (label) => {
    setCheckedItems((prevItems) => ({
      ...prevItems,
      [label]: {
        isChecked: !prevItems[label]?.isChecked,
        stringValue: getStringValue(label),
      },
    }));
  };

  const getCause = async () => {
    const selectedProblems = Object.entries(checkedItems)
      .filter(([_, { isChecked }]) => isChecked)
      .map(([_, { stringValue }]) => stringValue);

    const queryString = selectedProblems.join(",");

    const url = `http://localhost:8080/malfunction?evidence=${queryString}`;

    try {
      const response = await axios.get(url);
      console.log(response.data);
      setProbability(response.data);
    } catch (error) {
      console.error("Error making GET request:", error);
    }
  };

  const getStringValue = (label) => {
    return label.replace(/ /g, "_");
  };

  const problemLabels = [
    "Cant connenct with bluetooth headphones",
    "Screen tearing",
    "Blue screen",
    "No display",
    "Keyboard not working",
    "Mouse not working",
    "PC freezing",
    "PC overheating",
    "Internet not working",
    "PC not powering up",
    "PC not booting",
    "No audio",
    "Printer not working",
    "Cooler noise",
    "Slow performance",
    "USB port not working",
    "Games keep crashing",
  ];

  return (
    <div>
      <h2>PC Problems</h2>
      {problemLabels.map((label) => (
        <div key={label}>
          <input
            type="checkbox"
            id={label}
            checked={checkedItems[label]?.isChecked || false}
            onChange={() => handleChange(label)}
          />
          <label htmlFor={label}>{label}</label>
        </div>
      ))}

      <div>
        <h3>Probability:</h3>
        <ul>
          {Object.entries(probability).map(([label, value]) => (
            <li key={label}>{`${label.replace(/_/g, " ")}: ${value} %`}</li>
          ))}
        </ul>
      </div>
      <button onClick={getCause}>Get Cause</button>
    </div>
  );
};

export default PCProblems;
