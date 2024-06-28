import styled from 'styled-components';
import LoadingSpinner from '../components/LoadingSpinner';
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderWrapper = styled.header`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  z-index: 10;
  transition: padding 0.3s, height 0.3s;
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
  pointer-events: auto;
  z-index: 20;
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  top: 15px;
  right: 40px;
  z-index: 20;
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
  right: 0;
  background-color: #fff;
  padding: 10px;
  z-index: 50;
  min-width: 120px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
`;

const DropdownItem = styled.div`
  cursor: pointer;
  padding: 8px 16px;
  transition: background-color 0.3s;
  text-align: center;

  &:hover {
    background-color: #f9f9f9;
  }
`;

const Container = styled.div`
  margin-top: 30px;
  padding: 20px;
`;

const Topic = styled.div`
  margin-top: 60px;
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
  transition: transform 0.2s ease-in-out;
  &:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
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
      if (event.target.alt === 'userProfile') return;
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

        const accommodationPromises = reservationList.map(async (reservation) => {
          const accommodationResponse = await fetch(`http://localhost:8080/api/accommodations/${reservation.accommodationId}`);
          if (!accommodationResponse.ok) {
            throw new Error('Failed to fetch accommodation details');
          }
          const accommodationDetails = await accommodationResponse.json();
          return { ...reservation, accommodation: accommodationDetails };
        });

        const reservationsWithDetails = await Promise.all(accommodationPromises);

        setTimeout(() => {
          setReservations(reservationsWithDetails);
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
          {reservations.map((reservation) => (
            <ReservationCard key={reservation.id}>
              <ReservationImage src={reservation.accommodation?.accommodationImage?.imageUrl} alt={reservation.accommodation.name} />
              <ReservationDetails>
                <h2>{reservation.accommodation.name}</h2>
                <br/>
                <ReservationDetail>❤️‍🔥 체크인 날짜: {reservation.checkInDate}</ReservationDetail>
                <ReservationDetail>💔 체크아웃 날짜: {reservation.checkOutDate}</ReservationDetail>
                <ReservationDetail>👨🏻‍🦲 방문 인원: {reservation.visitorNumber}</ReservationDetail>
                <ReservationDetail>💰 총 가격: ₩{reservation.totalPrice.toLocaleString()}</ReservationDetail>
              </ReservationDetails>
            </ReservationCard>
          ))}
        </ReservationList>
      </Container>
    </div>
  );
};

export default MyPage;
