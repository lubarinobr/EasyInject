package com.lubarino.customdi;

import com.lubarino.customdi.annotations.Create;
import com.lubarino.customdi.factory.DIFactory;
import com.lubarino.customdi.service.MessageService;
import com.lubarino.customdi.service.SMSService;

public class Main {

    @Create
    private MessageService messageService;

    @Create
    private SMSService smsService;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Main main = new Main();
        main.start();
    }

    public void start() throws InstantiationException, IllegalAccessException {
        DIFactory context = new DIFactory(this);
        context.run();
        messageService.getMessage();
    }
}
