// LoadingSpinner.js
import React from 'react';
import styled from 'styled-components';

const SpinnerOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 로딩 스피너가 가장 위에 오도록 설정 */
`;

const Spinner = styled.div`
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top: 4px solid #ffffff;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;

  @keyframes spin {
    to {
      transform: rotate(360deg);
    }
  }
`;

const LoadingSpinner = ({ isLoading }) => {
  return (
    <>
      {isLoading && (
        <SpinnerOverlay>
          <Spinner />
        </SpinnerOverlay>
      )}
    </>
  );
};

export default LoadingSpinner;
