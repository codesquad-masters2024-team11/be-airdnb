import React, { useState, useEffect } from 'react';
import styled, { keyframes } from 'styled-components';
import Swal from "sweetalert2";
import { useNavigate } from 'react-router-dom';

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100; /* Adjust z-index value as needed */
`;

const slideIn = keyframes`
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
`;

const slideOut = keyframes`
  from {
    transform: translateX(0);
    opacity: 1;
  }
  to {
    transform: translateX(-100%);
    opacity: 0;
  }
`;

const ModalContent = styled.div`
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  max-width: 600px;
  z-index: 101; /* Ensure ModalContent is above ModalOverlay */
  position: relative; /* Ensure z-index works properly */
  max-height: 80vh; /* 최대 높이를 화면의 80%로 설정 */
  overflow-y: auto; /* 세로 스크롤을 허용하여 내용이 넘칠 때 스크롤바 표시 */
  animation: ${props => (props.slideOut ? slideOut : slideIn)} 0.5s forwards;
`;

const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
`;

const ReserveButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
  gap: 20px; /* 간격 추가 */
`;

const ReserveButton = styled.button`
  background-color: #ff385c;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  width: 150px; /* 버튼의 넓이를 늘림 */
  transition: background-color 0.3s ease;

  &:hover {
    background-color: darkred;
  }
`;

const CompleteButton = styled(ReserveButton)`
  background-color: #4CAF50; /* 완료 버튼의 배경 색상 */
  
  &:hover {
    background-color: #45a049; /* 완료 버튼의 hover 색상 */
  }
`;

const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 20px;

  label {
    display: flex;
    flex-direction: column;
    font-size: 14px;
    font-weight: bold;
  }

  input {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-top: 5px;
  }

  h2 {
    text-align: center;
    margin-bottom: 20px;
  }

  p {
    text-align: center;
    margin-bottom: 20px;
  }
`;



const AccommodationDetails = ({ accommodation, visitorNumber, checkInDate, checkOutDate, onReserveClick }) => (
  <div>
    <h2>{accommodation.name}</h2>
    <p>💁🏻‍♀️ 호스트 : {accommodation.hostId}</p>
    <p>🏠 주소 : {accommodation.address}</p>
    <p>💵 가격 : ₩{accommodation.price.toLocaleString()} /박</p>
    <p>📞 전화번호 : {accommodation.phoneNumber}</p>
    <p>🛌 최대 수용 인원 : {accommodation.maxCapacity}명</p>
    <p>🧹 청소비 : ₩{accommodation.cleaningFee.toLocaleString()}</p>
    <p>🚪 객실 이름 : {accommodation.roomType}</p>
    <p>😀 편의 시설 : {accommodation.accommodationAmenities.length > 0 ? accommodation.accommodationAmenities.map(item => item.name).join(', ') : '없음'}</p>
    <img
      src={accommodation.accommodationImage.imageUrl}
      alt={accommodation.name}
      style={{ width: '100%', maxHeight: '400px', objectFit: 'cover' }}
    />
    <ReserveButtonWrapper>
      <ReserveButton onClick={() => onReserveClick(accommodation)}>예약하기</ReserveButton>
    </ReserveButtonWrapper>
  </div>
);

const ReservationForm = ({ onBackClick, visitorNumber, onSubmit }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <FormWrapper>
      <h2>예약하기</h2>
      <p>예약 정보를 입력해주세요.</p>
      <label>
        이메일:
        <input type="email" name="email" value={formData.email} onChange={handleChange} />
      </label>
      <label>
        전화번호:
        <input type="tel" name="phone" value={formData.phone} onChange={handleChange} />
      </label>
      <ReserveButtonWrapper>
        <ReserveButton onClick={onBackClick} visitorNumber={visitorNumber}>뒤로가기</ReserveButton>
        <CompleteButton onClick={handleSubmit}>완료</CompleteButton>
      </ReserveButtonWrapper>
    </FormWrapper>
  );
};

const Modal = ({ isOpen, onClose, accommodation, visitorNumber, checkInDate, checkOutDate}) => {
  const [showReservationForm, setShowReservationForm] = useState(false);
  const [slideOut, setSlideOut] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isOpen) {
      setShowReservationForm(false);
      setSlideOut(false);
    }
  }, [isOpen]);

  const handleReserveClick = () => {
    setSlideOut(true);
    setTimeout(() => {
      setShowReservationForm(true);
      setSlideOut(false);
    }, 100); // 애니메이션 시간과 동일하게 설정
  };

  const handleBackClick = () => {
    setSlideOut(true);
    setTimeout(() => {
      setShowReservationForm(false);
      setSlideOut(false);
    }, 100); // 애니메이션 시간과 동일하게 설정
  };

  const handleSubmit = async (formData) => {
    const reservationRequest = {
      checkInDate: checkInDate,
      checkOutDate: checkOutDate,
      visitorNumber: visitorNumber,
      memberId: "Zzawang",
      accommodationId: accommodation.id,
    };

    const url = `http://localhost:8080/api/reservation`;

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(reservationRequest),
      });

      if (response.ok) {
        Swal.fire({
          icon: "success",
          title: "예약 성공",
          showConfirmButton: false,
          timer: 1500
        }).then((res) => {
          if (res.isDismissed) { // Swal.fire with timer returns isDismissed instead of isConfirmed
            navigate('/');
          }
        });
      }
    }catch (error) {
      Swal.fire({
        icon: "error",
        title: "서버 오류가 발생했습니다.\n다시 시도해주세요.",
        showConfirmButton: true
      });
    }
  };

  if (!isOpen) return null;

  return (
    <ModalOverlay onClick={onClose}>
      <ModalContent onClick={(e) => e.stopPropagation()} slideOut={slideOut}>
        <CloseButton onClick={onClose}>×</CloseButton>
        {showReservationForm ? (
          <ReservationForm onBackClick={handleBackClick} onSubmit={handleSubmit} />
        ) : (
          <AccommodationDetails
          accommodation={accommodation}
          visitorNumber={visitorNumber}
          checkInDate={checkInDate}
          checkOutDate={checkOutDate}
          onReserveClick={(accommodation) => handleReserveClick(accommodation, visitorNumber, checkInDate, checkOutDate)}
          />
        )}
      </ModalContent>
    </ModalOverlay>
  );
};

export default Modal;
