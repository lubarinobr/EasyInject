package com.lubarino.customdi.service;

import com.lubarino.customdi.annotations.Component;

@Component
public class SMSServiceImpl implements SMSService {

    @Override
    public void send() {
        System.out.println("Sent a message");
    }
}
