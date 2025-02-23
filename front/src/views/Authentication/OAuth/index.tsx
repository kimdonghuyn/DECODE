import React, { useEffect } from 'react';
import { useNavigate, useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

export default function OAuth() {
    const { token, expirationTime } = useParams();
    const [cookie, setCookie] = useCookies();
    const navigate = useNavigate();

    useEffect(() => {
        if (!token || !expirationTime) {
            console.warn("Missing token or expirationTime");
            return;
        }

        console.log(`Received token: ${token}, expiration: ${expirationTime}`);

        const expiresInMs = Number(expirationTime) * 1000; // 🔥 초 → 밀리초 변환
        if (isNaN(expiresInMs) || expiresInMs <= 0) {
            console.error("Invalid expirationTime:", expirationTime);
            return;
        }

        const expires = new Date(Date.now() + expiresInMs); // 🔥 현재 시간 + 만료 시간
        setCookie('accessToken', token, { expires, path: '/' });

        navigate('/'); // 🔥 홈으로 리디렉트

    }, [token, expirationTime, navigate, setCookie]); // 🔥 의존성 배열 수정

    return null; // 불필요한 Fragment (`<> </>`) 대신 `null` 반환
}