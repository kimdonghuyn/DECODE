import React, {ChangeEvent, KeyboardEvent, useRef, useState} from 'react'
import './style.css';
import {useNavigate} from "react-router-dom";

export default function SignIn() {
    const navigate = useNavigate();

    const onChatButtonClickHandler = () => {
        navigate('/chat');
    };

    return (
        <div id='sign-in-wrapper'>
            <div className='sign-in-image'></div>
            <div className='sign-in-container'>
                <div className='sign-in-box'>
                    <div className='sign-in-title'>{'DECODE Talk 📩'}</div>
                    <div className='sign-in-content-box'>
                        <div className='sign-in-content-button-box'>
                            <div className='primary-button-lg full-width' onClick={onChatButtonClickHandler}>{'채팅 시작'}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}