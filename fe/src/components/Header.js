import React, { useState, useEffect, useRef } from 'react';
import Typewriter from 'typewriter-effect';
import styled from 'styled-components';
import SearchBar from '../components/SearchBar';
import FilterBar from '../components/FilterBar'; // Import FilterBar here
import { useNavigate } from 'react-router-dom';

<Typewriter
  options={{
    autoStart: true,
    loop: true,
  }}
/>

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
  padding: ${(props) => (props.shrink ? '10px 20px' : '20px')};
  height: ${(props) => (props.shrink ? '140px' : '160px')}; /* Adjusted height */
`;

const TopSection = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
`;

const Logo = styled.img`
  position: absolute;
  top: 10px;
  left: 20px;
  height: 45px;
  cursor: pointer;
  pointer-events: auto; /* Enable pointer events */
  z-index: 20; /* Ensure logo is above other elements */
`;

const CenteredContainer = styled.div`
  width: ${(props) => (props.shrink ? '70%' : '100%')};
  display: flex;
  justify-content: center;
  position: relative;
  top: 0;
  transition: width 0.3s;
  z-index: 10; /* Ensure SearchBar is below the logo */
`;

const RightSection = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  top: 10px;
  right: 20px;
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

const Header = ({ shrink }) => {
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

  const handleHelpClick = () => {
    navigate('/help');
    setDropdownOpen(false);
  };

  const handleLogoutClick = () => {
    console.log('로그아웃 버튼 클릭');
    setDropdownOpen(false);
    navigate('/');
  };

  return (
    <HeaderWrapper shrink={shrink}>
      <TopSection>
        <Logo src="/assets/images/logo.png" alt="Logo" onClick={handleLogoClick} />
        <CenteredContainer shrink={shrink}>
          <SearchBar onSearch={() => {}} />
        </CenteredContainer>
        <RightSection>
          <HostLink onClick={handleHostLinkClick}>호스트로 전환하기</HostLink>
          <ProfileImage
            src="/assets/images/user-profile.png"
            alt="userProfile"
            onClick={handleProfileImageClick}
          />
          {dropdownOpen && (
            <DropdownMenu ref={dropdownRef}>
              <DropdownItem onClick={handleHelpClick}>도움</DropdownItem>
              <DropdownItem onClick={handleLogoutClick}>로그아웃</DropdownItem>
            </DropdownMenu>
          )}
        </RightSection>
      </TopSection>
      <FilterBar />
    </HeaderWrapper>
  );
};

export default Header;
