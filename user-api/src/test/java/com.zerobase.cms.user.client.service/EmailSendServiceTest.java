package com.zerobase.cms.user.client.service;


import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.service.customer.EmailSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;
    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void EmailTest(){
//        mailgunClient.sendEmail(null);
        emailSendService.sendEmail();
    }

}