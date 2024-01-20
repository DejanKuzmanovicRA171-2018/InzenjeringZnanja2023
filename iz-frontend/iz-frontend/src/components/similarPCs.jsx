import React, { useState } from "react";

const SimilarPCsPopup = ({ similarPCs, onClose }) => {
  return (
    <div className="popup">
      <div className="popup-content">
        <h2>Similar PCs</h2>
        <button onClick={onClose}>Close</button>
        <div>
          {similarPCs.map((pc, index) => (
            <div key={index}>
              <h3>PC {index + 1}</h3>
              <div>
                <strong>CPU:</strong> {pc.cpu.name}
              </div>
              <div>Clock Speed: {pc.cpu.cpuClockSpeed} MHz</div>
              <div>Cores: {pc.cpu.cpuNumOfCores}</div>

              <div>
                <strong>GPU:</strong> {pc.gpu.name}
              </div>
              <div>Clock Speed: {pc.gpu.gpuClockSpeed} MHz</div>
              <div>Cores: {pc.gpu.gpuNumOfCores}</div>
              <div>VRAM: {pc.gpu.gpuVRAM} GB</div>

              <div>
                <strong>RAM:</strong> {pc.ram.name}
              </div>
              <div>Clock Speed: {pc.ram.ramClockSpeed} MHz</div>
              <div>Size: {pc.ram.sizeOfRAM} GB</div>

              <div>
                <strong>Storage:</strong> {pc.storage.name}
              </div>
              <div>Write Speed: {pc.storage.writeSpeed} MB/s</div>
              <div>Capacity: {pc.storage.storageCapacity} GB</div>
              <div>RPM: {pc.storage.rpm}</div>

              <div>
                <strong>Motherboard:</strong> {pc.motherboard.name}
              </div>
              <div>RAM Slots: {pc.motherboard.numOfRAMSlotsM}</div>
              <div>RAM Capacity: {pc.motherboard.ramCapacityM} GB</div>

              <div>
                <strong>Cooling:</strong> {pc.cooling.name}
              </div>
              <div>Power: {pc.cooling.coolingPower} W</div>

              <div>
                <strong>Power Supply:</strong> {pc.psu.name}
              </div>
              <div>Power: {pc.psu.psuPower} W</div>

              <div>
                <strong>PC Case:</strong> {pc.pcCase.name}
              </div>

              <hr />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SimilarPCsPopup;
