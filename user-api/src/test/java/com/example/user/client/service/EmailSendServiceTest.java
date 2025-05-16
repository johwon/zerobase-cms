package com.example.user.client.service;

import com.example.user.service.EmailSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void EmailTest() {
        // need test code
        emailSendService.sendEmail();
    }
}