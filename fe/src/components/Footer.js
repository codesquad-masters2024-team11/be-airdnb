import React from 'react';
import styled from 'styled-components';

const FooterWrapper = styled.footer`
  background-color: #f7f7f7;
  padding: 20px;
`;

const FooterContent = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`;

const FooterSection = styled.div`
  flex: 1 1 10%;
  min-width: 200px;
  margin: 10px;

  h3 {
    margin-top: 0;
    color: #333;
  }

  ul {
    list-style: none;
    padding: 0;
  }

  li {
    margin: 5px 0;
    color: #666;
  }
`;

const Footer = () => {
  return (
    <FooterWrapper>
      <FooterContent>
        <FooterSection>
          <h3>소개</h3>
          <ul>
            <li>여행 방법</li>
            <li>뉴스룸</li>
            <li>투자자 정보</li>
            <li>채용정보</li>
            <li>참여할 터치</li>
          </ul>
        </FooterSection>
        <FooterSection>
          <h3>커뮤니티</h3>
          <ul>
            <li>다양성 및 소속감</li>
            <li>블로그</li>
            <li>어소시에이트</li>
            <li>게스트 추천</li>
          </ul>
        </FooterSection>
        <FooterSection>
          <h3>호스트하기</h3>
          <ul>
            <li>호스트하기</li>
            <li>호스트 지원</li>
            <li>자주 묻는 질문</li>
          </ul>
        </FooterSection>
        <FooterSection>
          <h3>지원</h3>
          <ul>
            <li>코로나19 대응 방안</li>
            <li>도움이 필요하세요?</li>
            <li>여행 취소 옵션</li>
            <li>신뢰와 안전</li>
          </ul>
        </FooterSection>
      </FooterContent>
      <p style={{ textAlign: 'center', marginTop: '20px', color: '#999' }}>
        &copy; 2021 Logo, Inc. 개인정보처리방침 | 이용약관 | 한국의 변경된 환불 정책 | 회사 세부정보
      </p>
    </FooterWrapper>
  );
};

export default Footer;
