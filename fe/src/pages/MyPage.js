import styled from 'styled-components';
import LoadingSpinner from '../components/LoadingSpinner'; // LoadingSpinner 컴포넌트 추가
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderWrapper = styled.header`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-direction: column; // Make sure elements are stacked vertically
  justify-content: center;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  z-index: 10;
  transition: padding 0.3s, height 0.3s; /* height 트랜지션 추가 */
  padding: 20px;
  height: 50px;
`;

const TopSection = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
`;

const Logo = styled.img`
  position: absolute;
  top: 15px;
  left: 40px;
  height: 45px;
  cursor: pointer;
  pointer-events: auto; /* Enable pointer events */
  z-index: 20; /* Ensure logo is above other elements */
`;


const RightSection = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  top: 15px;
  right: 40px;
  z-index: 20; /* Ensure logo is a
`;

const HostLink = styled.button`
  cursor: pointer;
  background-color: #ff385c;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 8px 16px;
  margin-right: 20px;

  &:hover {
    background-color: darkred;
  }
`;

const ProfileImage = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
`;

const DropdownMenu = styled.div`
  position: absolute;
  top: 60px;
  right: 0; /* 오른쪽 정렬 */
  background-color: #fff;
  padding: 10px;
  z-index: 50;
  min-width: 120px; /* 최소 너비 설정 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 더 진한 그림자 효과 추가 */
  border-radius: 8px; /* 둥근 모서리 설정 */
`;

const DropdownItem = styled.div`
  cursor: pointer;
  padding: 8px 16px;
  transition: background-color 0.3s;
  text-align: center; /* 텍스트를 오른쪽 정렬 */

  &:hover {
    background-color: #f9f9f9; /* 호버 시 배경색 변경 */
  }
`;

const Container = styled.div`
  margin-top: 30px;
  padding: 20px;
`;

const Topic = styled.div`
  font-size: 36px;
  font-weight: bold;
  margin-bottom: 20px;
`;

const ReservationList = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
`;

const ReservationCard = styled.div`
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
  overflow: hidden;
  transition: transform 0.2s ease-in-out; /* Add a transition for smooth scaling */
  &:hover {
    transform: scale(1.05); /* Scale up to 105% on hover */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Add a subtle shadow on hover */
  }
`;

const ReservationImage = styled.img`
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
`;

const ReservationDetails = styled.div`
  padding: 16px;
    font-weight: bold;
`;

const ReservationDetail = styled.p`
  margin: 10px 0;
    font-weight: bold;
`;

const MyPage = () => {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if(event.target.alt === 'userProfile') return;
      setDropdownOpen(false);
    };

    document.addEventListener('click', handleClickOutside);

    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, [dropdownOpen]);

  const handleHostLinkClick = () => {
    navigate('/host');
  };

  const handleProfileImageClick = () => {
    setDropdownOpen((prev) => !prev);
  };

  const handleLogoClick = () => {
    navigate('/');
  };

  const handleMyPageClick = () => {
    navigate('/myPage');
    setDropdownOpen(false);
  };

  const handleHelpClick = () => {
    navigate('/help');
    setDropdownOpen(false);
  };

  const handleLogoutClick = () => {
    console.log('로그아웃 버튼 클릭');
    setDropdownOpen(false);
    navigate('/');
  };

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/reservation/member/Zzawang`);
        if (!response.ok) {
          throw new Error('Failed to fetch reservations');
        }
        const data = await response.json();
        const reservationList = Object.keys(data).map(key => data[key]);

        // Simulate a delay to show the loading spinner
        setTimeout(() => {
          setReservations(reservationList);
          setLoading(false);
        }, 1000);
      } catch (error) {
        console.error('Error fetching reservations:', error);
        setLoading(false);
      }
    };

    fetchReservations();
  }, []);

  if (loading) {
    return <LoadingSpinner isLoading={loading} />;
  }

  return (
    <div>
    <HeaderWrapper>
      <TopSection>
        <Logo src="/assets/images/logo.png" alt="Logo" onClick={handleLogoClick} />
        <RightSection>
          <HostLink onClick={handleHostLinkClick}>호스트로 전환하기</HostLink>
          <ProfileImage
            src="/assets/images/user-profile.png"
            alt="userProfile"
            onClick={handleProfileImageClick}
          />
          {dropdownOpen && (
            <DropdownMenu ref={dropdownRef}>
              <DropdownItem onClick={handleMyPageClick}>마이페이지</DropdownItem>
              <DropdownItem onClick={handleHelpClick}>도움</DropdownItem>
              <DropdownItem onClick={handleLogoutClick}>로그아웃</DropdownItem>
            </DropdownMenu>
          )}
        </RightSection>
      </TopSection>
    </HeaderWrapper>
    <Container>
      <Topic>짜왕님의 예약 현황</Topic>
      <ReservationList>
        {reservations.map((reservation, index) => (
          <ReservationCard key={reservation.id}>
            <ReservationImage src={`/assets/images/${(index+1)%11}.jpg`} />
            <ReservationDetails>
              <ReservationDetail>❤️‍🔥 체크인 날짜 : {reservation.checkInDate}</ReservationDetail>
              <ReservationDetail>💔 체크아웃 날짜 : {reservation.checkOutDate}</ReservationDetail>
              <ReservationDetail>👨🏻‍🦲 방문 인원 : {reservation.visitorNumber}</ReservationDetail>
              <ReservationDetail>💰 총 가격 : ₩{reservation.totalPrice.toLocaleString()}</ReservationDetail>
            </ReservationDetails>
          </ReservationCard>
        ))}
      </ReservationList>
    </Container>
    </div>
  );
};

export default MyPage;
