package com.lubarino.customdi.service;

import com.lubarino.customdi.annotations.Component;
import com.lubarino.customdi.annotations.Create;

@Component
public class MessageServiceImpl implements MessageService {

    @Create
    private static SMSService smsService;

    @Override
    public void getMessage() {
        System.out.println("Sending a new message");

        smsService.send();
    }
}
