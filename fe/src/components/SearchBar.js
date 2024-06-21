import React, { useState } from 'react';
import styled from 'styled-components';
import GuestsPicker from './GuestsPicker';
import DatePicker from './DatePicker';
import LocationSelector from './LocationSelector';
import { useNavigate } from 'react-router-dom';
import LoadingSpinner from './LoadingSpinner'; // LoadingSpinner 컴포넌트 추가
import searchIcon from './assets/search-icon.png'; // 검색 아이콘 이미지 임포트

const SearchBarWrapper = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 20px 20px 0;
  padding: 10px 10px 10px -10px;
  border: none;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  width: 65%;
  z-index: 10;
`;

const FlexItem = styled.div`
  flex-grow: ${({ grow }) => grow};
  display: flex;
  border: none;
  justify-content: center;
  align-items: center;
`;

const Button = styled.button`
  width: 100%;
  height: 100%;
  border: none;
  background-color: transparent;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
  padding: 20px 20px 20px 0; /* 버튼 내부 여백 조정 */

  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 35px; /* 이미지 너비 */
    height: 35px; /* 이미지 높이 */
    background-image: url(${searchIcon});
    background-size: cover;
    background-position: center;
  }

  span {
    position: absolute;
    top: -9999px;
    left: -9999px;
  }
`;

const SearchBar = ({ onSearch }) => {
  const [location, setLocation] = useState('');
  const [checkIn, setCheckIn] = useState(null);
  const [checkOut, setCheckOut] = useState(null);
  const [guests, setGuests] = useState({ adults: 0, children: 0, infants: 0 });
  const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가
  const navigate = useNavigate();

  const handleLocationSelect = (selectedLocation) => {
    setLocation(selectedLocation);
  };

  const formatDate = (date) => {
    if (!date) return null;
    const d = new Date(date);
    const month = `${d.getMonth() + 1}`.padStart(2, '0');
    const day = `${d.getDate()}`.padStart(2, '0');
    const year = d.getFullYear();
    return `${year}-${month}-${day}`;
  };

  const handleSearch = async () => {
    setIsLoading(true); // 검색 요청 시작 시 로딩 상태 설정

    const query = new URLSearchParams({
      region: location,
      checkInDate: formatDate(checkIn),
      checkOutDate: formatDate(checkOut),
      guestNumber: guests.adults + guests.children + guests.infants,
    }).toString();

    const url = `http://localhost:8080/api/accommodations/search?${query}`;

    try {
      console.log('Fetching URL:', url);
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error('Failed to fetch');
      }
      const results = await response.json();
      console.log('Search results:', results);
      navigate('/search', { state: { accommodations: results } });
    } catch (error) {
      console.error('Error fetching search results:', error);
    } finally {
      setIsLoading(false); // 요청 처리 완료 시 로딩 상태 해제
    }
  };

  return (
    <>
      <SearchBarWrapper>
        <FlexItem grow={1}>
          <LocationSelector onSelectLocation={handleLocationSelect} />
        </FlexItem>
        <FlexItem grow={2}>
          <DatePicker label="체크인" date={checkIn} setDate={setCheckIn} />
        </FlexItem>
        <FlexItem grow={2}>
          <DatePicker label="체크아웃" date={checkOut} setDate={setCheckOut} />
        </FlexItem>
        <FlexItem grow={1}>
          <GuestsPicker guests={guests} setGuests={setGuests} />
        </FlexItem>
        <FlexItem grow={1}>
          <Button onClick={handleSearch}>
            <span>검색하기</span>
          </Button>
        </FlexItem>
      </SearchBarWrapper>
      <LoadingSpinner isLoading={isLoading} /> {/* 로딩 상태를 나타내는 Spinner */}
    </>
  );
};

export default SearchBar;
