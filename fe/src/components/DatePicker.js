import React, { useState } from 'react';
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styled from 'styled-components';

const DatePickerWrapper = styled.div`
  position: relative;
`;

const DatePickerButton = styled.button`
  padding: 10px;
  margin: 0 5px;
  border: none;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  z-index: 20; /* Ensure it stays above other elements */
  width: 100%;
`;

const DatePickerDropdown = styled.div`
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
`;

const DatePicker = ({ label, date, setDate }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleDateChange = (selectedDate) => {
    setDate(selectedDate);
    setIsOpen(false);
  };

  return (
    <DatePickerWrapper>
      <DatePickerButton onClick={() => setIsOpen(!isOpen)}>
        {label}: {date ? date.toLocaleDateString() : '날짜 선택'}
      </DatePickerButton>
      {isOpen && (
        <DatePickerDropdown show={isOpen}>
          <ReactDatePicker
            selected={date}
            onChange={handleDateChange}
            inline
          />
        </DatePickerDropdown>
      )}
    </DatePickerWrapper>
  );
};

export default DatePicker;
