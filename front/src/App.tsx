import React, {ChangeEvent, useEffect, useState} from 'react';
import './App.css';
import InputBox from './components/InputBox';
import {Navigate, Route, Routes} from 'react-router-dom';
import SignUp from './views/Authentication/SignUp';
import SignIn from './views/Authentication/SignIn';
import OAuth from './views/Authentication/OAuth';
import Decode from 'views/Authentication/Decode';
import Chat from "./views/Authentication/Chat";
import {Cookies} from "react-cookie";

function PrivateRoute({ children }: { children: JSX.Element }) {
    const cookies = new Cookies();
    const token = cookies.get("accessToken");

    if (!token) {
        return <Navigate to="/auth/sign-in" replace />;
    }

    return children;
}


function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
    const cookies = new Cookies();

    useEffect(() => {
        const token = cookies.get("accessToken");
        setIsAuthenticated(!!token);
    }, []);

    // 로딩 상태 처리 (null일 때 렌더링 방지)
    if (isAuthenticated === null) {
        return <div>Loading...</div>;
    }


    return (
        <Routes>
            <Route path="/" element={<PrivateRoute><Decode/></PrivateRoute>}/>
            <Route path="/chat" element={<PrivateRoute><Chat/></PrivateRoute>}/>§
            <Route path="/auth">
                <Route path="sign-up" element={<SignUp/>}/>
                <Route path="sign-in" element={<SignIn/>}/>
                <Route path="oauth-response/:token/:expirationTime" element={<OAuth/>}/>
            </Route>
        </Routes>
    );
}

export default App;
