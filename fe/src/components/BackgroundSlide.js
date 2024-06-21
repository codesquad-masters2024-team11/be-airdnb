import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

const images = [
  '/assets/images/1.jpg',
  '/assets/images/2.jpg',
  '/assets/images/3.jpg',
  '/assets/images/4.jpg',
  '/assets/images/5.jpg',
  '/assets/images/6.jpg',
  '/assets/images/7.jpg',
  '/assets/images/8.jpg',
  '/assets/images/9.jpg',
  '/assets/images/10.jpg'
];

const BackgroundSlideshow = styled.div`
  position: relative;
  top: 0;
  height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const SlideshowImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: ${(props) => (props.active ? 1 : 0)};
  transition: opacity 1s ease-in-out;
`;

const Arrow = styled.div`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  font-size: 2rem;
  color: white;
  z-index: 1;

  &.left {
    left: 10px;
  }

  &.right {
    right: 10px;
  }
`;

const Dots = styled.div`
  position: absolute;
  bottom: 10px;
  display: flex;
  justify-content: center;
  width: 100%;
`;

const Dot = styled.div`
  width: 10px;
  height: 10px;
  margin: 0 5px;
  border-radius: 50%;
  background-color: ${(props) => (props.active ? 'white' : 'gray')};
  cursor: pointer;
`;


const BackgroundSlide = () => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 2700); // 2.7초마다 이미지 변경

    return () => clearInterval(intervalId); // 컴포넌트 언마운트 시 인터벌 클리어
  }, []);

  const goToPreviousImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
  };

  const goToNextImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  const goToImage = (index) => {
    setCurrentImageIndex(index);
  };

  return (
    <BackgroundSlideshow>
        <Arrow className="left" onClick={goToPreviousImage}>
          &#9664;
        </Arrow>
        <Arrow className="right" onClick={goToNextImage}>
          &#9654;
        </Arrow>
        {images.map((image, index) => (
          <SlideshowImage
            key={index}
            src={image}
            alt={`Slideshow image ${index + 1}`}
            active={index === currentImageIndex}
          />
        ))}
        <Dots>
          {images.map((_, index) => (
            <Dot key={index} active={index === currentImageIndex} onClick={() => goToImage(index)} />
          ))}
        </Dots>
      </BackgroundSlideshow>
  );
};

export default BackgroundSlide;
