import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import Footer from '../components/Footer';
import BackgroundSlide from '../components/BackgroundSlide';

const PageContainer = styled.div`
  position: relative;
`;

const ContentContainer = styled.div`
  padding-top: 160px; // Adjust to match the total height of Header + FilterBar
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
      <ContentContainer>
        <BackgroundSlide />
        <Blank />
        <Footer />
      </ContentContainer>
    </PageContainer>
  );
};

export default HomePage;
