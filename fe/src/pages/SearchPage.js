import React, { useState } from 'react';
import KakaoMap from '../components/KakaoMap';
import { useLocation } from 'react-router-dom';
import Header from '../components/Header';
import AccommodationList from '../components/AccommodationList';
import Modal from '../components/Modal';

const SearchPage = () => {
  const location = useLocation();
  const coordinate = location.state?.coordinate;
  const accommodations = location.state?.accommodations;
  const visitorNumber = location.state?.visitorNumber;
  const checkInDate = location.state?.checkInDate;
  const checkOutDate = location.state?.checkOutDate;

  const [selectedAccommodation, setSelectedAccommodation] = useState(null);

  const openModal = (accommodation, visitorNumber, checkInDate, checkOutDate) => {
    setSelectedAccommodation(accommodation);
  };

  const closeModal = () => {
    setSelectedAccommodation(null);
  };

  return (
    <div>
      <Header shrink={false} style={{ zIndex: 1 }} /> {/* Header의 z-index 설정 */}
      <div style={{ paddingTop: '200px' }}>
        <div style={{ display: 'flex', height: '72vh' }}>
          <div style={{ flex: 1, overflowY: 'auto' }}>
            <AccommodationList accommodations={accommodations} visitorNumber={visitorNumber} checkInDate={checkInDate} checkOutDate={checkOutDate} openModal={openModal} />
          </div>
          <div style={{ flex: 0.7, zIndex: 0 }}> {/* KakaoMap의 z-index 설정 */}
            <KakaoMap coordinate={coordinate} accommodations={accommodations} />
          </div>
        </div>
      </div>
      <Modal isOpen={selectedAccommodation !== null} onClose={closeModal} accommodation={selectedAccommodation} visitorNumber={visitorNumber} checkInDate={checkInDate} checkOutDate={checkOutDate}  style={{ zIndex: 2 }} /> {/* Modal의 z-index 설정 */}
    </div>
  );
};

export default SearchPage;
