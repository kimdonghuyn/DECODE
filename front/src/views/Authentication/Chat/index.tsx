import './style.css';
import React, { useEffect, useState, useRef } from "react";

export default function Chat() {
    const [messages, setMessages] = useState<string[]>([]);
    const [inputMessage, setInputMessage] = useState<string>('');
    const ws = useRef<WebSocket | null>(null);
    const chatListRef = useRef<HTMLDivElement | null>(null);

    // 웹소켓 연결
    const connectWebSocket = () => {
        if (ws.current) {
            ws.current.close(); // 기존 WebSocket이 있다면 종료
        }

        ws.current = new WebSocket("ws://localhost:4040/ws/chat");

        ws.current.onopen = () => {
            console.log("✅ WebSocket 연결 성공");
        };

        ws.current.onmessage = (event) => {
            console.log("📩 수신된 메시지:", event.data);
            setMessages((prev) => [...prev, event.data]);
        };

        ws.current.onerror = (error) => {
            console.error("❌ WebSocket 오류 발생:", error);
        };

        ws.current.onclose = (event) => {
            console.warn("⚠️ WebSocket 연결 종료. 3초 후 재연결 시도...");
            setTimeout(() => connectWebSocket(), 3000); // 3초 후 재연결 시도
        };
    };

    // 컴포넌트 마운트 시 WebSocket 연결
    useEffect(() => {
        connectWebSocket();

        return () => {
            ws.current?.close();
        };
    }, []);

    // 메시지 전송
    const isSending = useRef(false);

    const sendChat = () => {
        if (ws.current && ws.current.readyState === WebSocket.OPEN && !isSending.current) {
            isSending.current = true;
            ws.current.send(inputMessage);
            setMessages(prev => [...prev, `나: ${inputMessage}`]);

            setTimeout(() => {
                isSending.current = false;
            }, 100);

            setInputMessage("");
        } else {
            console.warn("⚠️ WebSocket이 아직 연결되지 않았거나 중복 실행 방지됨.");
        }
    };

    // 최신 메시지가 추가될 때 자동 스크롤
    useEffect(() => {
        if (chatListRef.current) {
            chatListRef.current.scrollTo(0, chatListRef.current.scrollHeight);
        }
    }, [messages]);

    return (
        <div id="sign-in-wrapper">
            <div className="chat-container">
                <div className="chat-list" ref={chatListRef}>
                    {messages.map((msg, index) => (
                        <div key={index} className={msg.startsWith("나:") ? "my-chat" : ""}>
                            <h2>{msg}</h2>
                        </div>
                    ))}
                </div>
            </div>
            <div className="sign-in-container">
                <div className="sign-in-box">
                    <div className="sign-in-title">{'채팅을 해보아요'}</div>
                    <div className="sign-in-content-box">
                        <div className="sign-in-content-button-box">
                            <div className='sign-in-content-input-box'>
                                <input
                                    className='input-box-input'
                                    placeholder="문자를 입력해주세요."
                                    value={inputMessage}
                                    onChange={(e) => setInputMessage(e.target.value)}
                                />
                            </div>
                            <div className="primary-button-lg full-width" onClick={sendChat}>
                                {'전송'}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}