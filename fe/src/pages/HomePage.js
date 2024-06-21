import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import Footer from '../components/Footer';
import BackgroundSlide from '../components/BackgroundSlide';

const HEADER_HEIGHT = '100px'; // 헤더 높이

const PageContainer = styled.div`
  position: relative;
  padding-top: ${HEADER_HEIGHT}; // 헤더 높이만큼 패딩 추가
`;

const Blank = styled.img`
  height: 1000px;
`;

const HomePage = () => {
  const [scrollPosition, setScrollPosition] = useState(0);

  useEffect(() => {
    const handleScroll = () => {
      setScrollPosition(window.scrollY);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <PageContainer>
      <Header shrink={scrollPosition > 0} />
      <BackgroundSlide/>
      <Blank />
      <Footer />
    </PageContainer>
  );
};

export default HomePage;
