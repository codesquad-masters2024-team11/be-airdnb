import React from 'react';
import styled from 'styled-components';

const Card = styled.div`
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  margin: 10px;
  width: calc(33.333% - 32px); /* 33.333% of the row minus the gaps */
  box-sizing: border-box;
  transition: transform 0.3s;
  cursor: pointer;

  &:hover {
    transform: scale(1.05);
  }
`;

const Image = styled.img`
  width: 100%;
  height: 200px;
  object-fit: cover;
`;

const Info = styled.div`
  padding: 10px;
`;

const AccommodationItem = ({ accommodation, visitorNumber, checkInDate, checkOutDate, openModal }) => {

  const { name, price, accommodationImage, address } = accommodation;

  const handleClick = () => {
    openModal(accommodation, visitorNumber, checkInDate, checkOutDate); // openModal 함수를 호출하여 숙소 정보를 전달
  };

  return (
    <>
      <Card onClick={handleClick}>
        <Image src={accommodationImage.imageUrl} alt={name} />
        <Info>
          <h3>{name}</h3>
          <p>{address}</p>
          <p>₩{price.toLocaleString()} /박</p>
        </Info>
      </Card>
    </>
  );
};

export default AccommodationItem;
