import React, { useState } from 'react';

const PricingComponent = () => {
  const [selectedPlan, setSelectedPlan] = useState('100,000-1,000,000');

  const handlePlanChange = (event) => {
    setSelectedPlan(event.target.value);
  };

  return (
    <div>
      <div>
        <span>요금</span>
        <select value={selectedPlan} onChange={handlePlanChange}>
          <option value="100,000-1,000,000">100,000-1,000,000</option>
        </select>
      </div>
    </div>
  );
};

export default PricingComponent;
