import React, { ChangeEvent, useState } from 'react';
import './App.css';
import InputBox from './components/InputBox';
import { Route, Routes } from 'react-router-dom';
import SignUp from './views/Authentication/SignUp';
import SignIn from './views/Authentication/SignIn';
import OAuth from './views/Authentication/OAuth';
import Decode from 'views/Authentication/Decode';
import Chat from "./views/Authentication/Chat";

function App() {
  const [id, setId] = useState<string>('');
  const onIdChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setId(value);
  };

  const onIdButtonClickHandler = () => {};

  return (
    <Routes>
      <Route path="/" element={<Decode />} />
        <Route path="/chat" element={<Chat />} />
      <Route path="/auth">
        <Route path="sign-up" element={<SignUp />} />
        <Route path="sign-in" element={<SignIn />} />
        <Route path="oauth-response/:token/:expirationTime" element={<OAuth />} />
      </Route>
    </Routes>
  );
}

export default App;
