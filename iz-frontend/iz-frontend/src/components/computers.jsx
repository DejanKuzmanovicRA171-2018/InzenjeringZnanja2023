import React from "react";
import "../styles.css";

const Computers = () => {
  return (
    <div>
      <h1>Computers</h1>
      <table className="tabela">
        <thead>
          <tr>
            <th>GPU</th>
            <th>CPU</th>
            <th>RAM</th>
            <th>Storage</th>
            <th>Cooling</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>GPU Data</td>
            <td>CPU Data</td>
            <td>RAM Data</td>
            <td>Storage Data</td>
            <td>Cooling Data</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default Computers;
