import React from 'react';

import Typewriter from 'typewriter-effect';
import BackgroundAnimation, { varColor2x} from '../components/BackgroundAnimation';
import { CssBaseline } from '@mui/material';

<Typewriter
  options={{
    autoStart: true,
    loop: true,
  }}
/>

const LoginPage = () => {
  return (
    <div>
      <CssBaseline />
      {/* 선호하는 대로 다른 variant를 사용할 수 있습니다 */}
      <BackgroundAnimation variant={varColor2x} />
      {/* 다른 variant를 사용한 더 많은 BackgroundAnimation 컴포넌트 추가 가능 */}
      <Typewriter
    onInit={(typewriter) => {
    typewriter
      .typeString('<h1>당신만을 위한 숙박 서비스, </h1>')
      .pauseFor(300)
      .typeString('<strong>Airb...</strong> ')
      .deleteChars(8)
      .typeString('<strong>Yourbnb</strong>')
      .pauseFor(100)
      .callFunction(() => {
        console.log('All strings were deleted');
      })
      .start();
  }}
/>
    </div>
  )
};

export default LoginPage;