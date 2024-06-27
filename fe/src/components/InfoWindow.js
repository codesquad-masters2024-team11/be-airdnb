import React from 'react';
import styled from 'styled-components';

const InfoWindowContainer = styled.div`
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
`;

const InfoWindowImage = styled.img`
  width: 100px;
  height: auto;
  border-radius: 4px;
  margin-right: 10px;
`;

const InfoWindowContent = styled.div`
  display: flex;
  align-items: center;
`;

const InfoWindowTitle = styled.h3`
  font-size: 16px;
  font-weight: bold;
  margin: 0;
`;

const InfoWindowPrice = styled.p`
  font-size: 14px;
  color: #666;
  margin: 4px 0 0;
`;

const InfoWindow = ({ accommodation }) => {
  const { name, price, accommodationImage } = accommodation;

  return (
    <InfoWindowContainer>
      <InfoWindowContent>
        <InfoWindowImage src={accommodationImage.imageUrl} alt={name} />
        <div>
          <InfoWindowTitle>{name}</InfoWindowTitle>
          <InfoWindowPrice>₩{price.toLocaleString()} /박</InfoWindowPrice>
        </div>
      </InfoWindowContent>
    </InfoWindowContainer>
  );
};

export default InfoWindow;
