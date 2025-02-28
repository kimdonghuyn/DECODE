import './style.css';
import React, { useEffect, useState, useRef } from "react";
import {Client} from "@stomp/stompjs";

export default function Chat() {
    const [messages, setMessages] = useState<string[]>([]);
    const [inputMessage, setInputMessage] = useState<string>('');
    const chatListRef = useRef<HTMLDivElement | null>(null);
    const client = useRef<Client | null>(null);

    // 웹소켓 연결
    const connect = () => {
        client.current = new Client({
            brokerURL: 'ws://localhost:4040/ws-connect',
            onConnect: (frame) => {
                console.log('Connected: ' + frame);
                subscribe();
            },
            onStompError: (frame) => {
                console.log('Broker reported error: ' + frame.headers['message']);
                console.log('Additional details: ' + frame.body);
            },
        });

        client.current.activate();
    }

    // 메시지 전송
    const isSending = useRef(false);

    const sendChat = () => {
        client.current?.publish({destination: '/pub/chat', body: JSON.stringify({message: inputMessage})});
        setInputMessage('');
    };

    const subscribe = () => {
        client.current?.subscribe('/sub/chat', (message) => {
            const newMessage = JSON.parse(message.body).message;
            setMessages((prev) => [...prev, newMessage]);
        });
    }

    // 최신 메시지가 추가될 때 자동 스크롤
    useEffect(() => {
        if (chatListRef.current) {
            chatListRef.current.scrollTo(0, chatListRef.current.scrollHeight);
        }
    }, [messages]);

    useEffect(() => {
        connect();

        return () => {
            if (client.current) {
                client.current.deactivate();
                client.current = null;
            }
        }
    }, []);

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
                            <form onSubmit={(e) => {
                                e.preventDefault();
                                sendChat();
                            }}>
                                <div className='sign-in-content-input-box'>
                                    <input
                                        className='input-box-input'
                                        placeholder="문자를 입력해주세요."
                                        value={inputMessage}
                                        onChange={(e) => setInputMessage(e.target.value)}
                                    />
                                </div>
                                <button className="primary-button-lg full-width">
                                    {'전송'}
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}