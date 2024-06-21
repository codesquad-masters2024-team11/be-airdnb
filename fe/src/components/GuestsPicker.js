import React, { useState } from 'react';
import styled from 'styled-components';

const GuestsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;

const Button = styled.button`
  padding: 10px;
  margin: 0 5px;
  border: none;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  z-index: 20; /* Ensure it stays above other elements */
`;

const Dropdown = styled.div`
  position: absolute;
  top: calc(100% + 20px);
  left: 0;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  padding: 20px;
  opacity: ${(props) => (props.show ? '1' : '0')};
  overflow: hidden;
  transition: opacity 0.3s ease, height 0.3s ease; /* Smooth transitions */
  z-index: 15; /* Ensure it stays above the button */
  width: 150px; /* Adjust the minimum width as needed */
`;

const Row = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
`;

const Label = styled.div`
  flex: 1;
  margin-right: 20px;
`;

const CountButton = styled.button`
  border: none;
  background-color: transparent;
  font-size: 20px;
  cursor: pointer;
  margin: 0 5px;
`;

const GuestsPicker = ({ guests, setGuests }) => {
  const [showDropdown, setShowDropdown] = useState(false);

  const handleGuestChange = (type, action) => {
    setGuests((prevGuests) => {
      const newGuests = { ...prevGuests };
      if (action === 'increment') {
        if (newGuests[type] < 99) {
          newGuests[type] += 1;
        }
      } else {
        newGuests[type] = Math.max(0, newGuests[type] - 1);
      }
      return newGuests;
    });
  };

  return (
    <GuestsWrapper>
      <Button onClick={() => setShowDropdown(!showDropdown)}>
        {guests.adults + guests.children === 0
          ? '게스트 추가'
          : `게스트 ${guests.adults + guests.children} 명`}
      </Button>
      <Dropdown show={showDropdown}>
        <Row>
          <Label>성인</Label>
          <CountButton onClick={() => handleGuestChange('adults', 'decrement')}>-</CountButton>
          {guests.adults}
          <CountButton onClick={() => handleGuestChange('adults', 'increment')}>+</CountButton>
        </Row>
        <Row>
          <Label>어린이</Label>
          <CountButton onClick={() => handleGuestChange('children', 'decrement')}>-</CountButton>
          {guests.children}
          <CountButton onClick={() => handleGuestChange('children', 'increment')}>+</CountButton>
        </Row>
        <Row>
          <Label>유아</Label>
          <CountButton onClick={() => handleGuestChange('infants', 'decrement')}>-</CountButton>
          {guests.infants}
          <CountButton onClick={() => handleGuestChange('infants', 'increment')}>+</CountButton>
        </Row>
      </Dropdown>
    </GuestsWrapper>
  );
};

export default GuestsPicker;
