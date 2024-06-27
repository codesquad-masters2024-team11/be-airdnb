import React from 'react';
import AccommodationItem from './AccommodationItem';
import styled from 'styled-components';

const AccommodationCount = styled.div`
  margin-top: 20px; /* 위쪽 여백 20px 설정 */
  margin-left: 20px; /* 왼쪽 여백 10px 설정 */
  font-weight: bold; /* 글씨 진하게 설정 */
`;

const List = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 16px;
`;

const AccommodationList = ({ accommodations, visitorNumber, checkInDate, checkOutDate, openModal }) => {  
  return (
    <div>
      <AccommodationCount>
        지도 표시 지역의 숙소 {accommodations.length}개
      </AccommodationCount>
      <List>
        {accommodations && accommodations.length > 0 ? (
          accommodations.map((accommodation) => (
            <AccommodationItem
              key={accommodation.id}
              accommodation={accommodation}
              visitorNumber={visitorNumber}
              checkInDate={checkInDate} 
              checkOutDate={checkOutDate} 
              openModal={openModal}
            />
          ))
        ) : (
          <p>검색 결과가 없습니다.</p>
        )}
      </List>
    </div>
  );
};

export default AccommodationList;
