import React, { useState } from 'react';
import styled from 'styled-components';

const LocationDropdown = styled.div`
  position: relative;
`;

const DropdownButton = styled.button`
  border : none;
  padding: 10px;
  margin: 0 5px;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  z-index: 20; /* SearchBarWrapper의 z-index보다 높게 설정 */
`;

const Dropdown = styled.div`
  position: absolute;
  top: calc(100% + 20px);
  left: 0;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  padding: 20px;
  opacity: ${(props) => (props.show ? '1' : '0')};
  height: ${(props) => (props.show ? 'auto' : 'auto')};
  overflow: hidden;
  transition: opacity 0.3s ease, height 0.3s ease; /* Adjust transition properties */

  z-index: 15; /* DropdownButton의 z-index보다 높게 설정 */
`;


const LocationOptionsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3열 그리드 */
  gap: 20px; /* 그리드 사이의 간격 */
`;

const LocationOption = styled.button`
  padding: 20px; /* 패딩 추가 */
  text-align: center;
  background-color: rgba(255, 255, 255, 0.9);
  border: none;
  cursor: pointer;
  border-radius: 20px; /* 동그란 형태의 모서리 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
  transition: background-color 0.3s, transform 0.1s;
  writing-mode: horizontal-tb;
  min-width: 120px; /* Adjust the minimum width as needed */

  &:hover {
    background-color: #f1f1f1;
    transform: scale(1.15); /* 확대 효과 */
  }
`;

const LocationSelector = ({ onSelectLocation }) => {
  const [open, setOpen] = useState(false);
  const [selectedLocation, setSelectedLocation] = useState('');

  const handleSelect = (location) => {
    setSelectedLocation(location);
    onSelectLocation(location);
    setOpen(false); // Close the dropdown after selection
  };

  return (
    <LocationDropdown>
      <DropdownButton onClick={() => setOpen(!open)}>
        {selectedLocation || '여행지 선택'}
      </DropdownButton>
      <Dropdown show={open}>
        <LocationOptionsGrid>
          <LocationOption onClick={() => handleSelect('서울')}>서울</LocationOption>
          <LocationOption onClick={() => handleSelect('인천')}>인천</LocationOption>
          <LocationOption onClick={() => handleSelect('경기')}>경기</LocationOption>
          <LocationOption onClick={() => handleSelect('제주')}>제주</LocationOption>
          <LocationOption onClick={() => handleSelect('부산')}>부산</LocationOption>
          <LocationOption onClick={() => handleSelect('울산')}>울산</LocationOption>
          <LocationOption onClick={() => handleSelect('전북')}>전북</LocationOption>
          <LocationOption onClick={() => handleSelect('전라남도')}>전남</LocationOption>
          <LocationOption onClick={() => handleSelect('광주')}>광주</LocationOption>
          <LocationOption onClick={() => handleSelect('충청')}>충청</LocationOption>
          <LocationOption onClick={() => handleSelect('대전')}>대전</LocationOption>
          <LocationOption onClick={() => handleSelect('경상북도')}>경북</LocationOption>
          <LocationOption onClick={() => handleSelect('경상남도')}>경남</LocationOption>
          <LocationOption onClick={() => handleSelect('강원')}>강원</LocationOption>
          <LocationOption onClick={() => handleSelect('세종')}>세종</LocationOption>
        </LocationOptionsGrid>
      </Dropdown>
    </LocationDropdown>
  );
};

export default LocationSelector;
