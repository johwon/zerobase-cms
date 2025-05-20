package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public void sendEmail(){
            SendMailForm form = SendMailForm.builder()
                    .from("no-reply@yourdomain.com")
                    .to("4971834@naver.com")
                    .subject("Test email from zero base")
                    .text("my text")
                    .build();
        try {
            Response response = mailgunClient.sendEmail(form);
            System.out.println("메일전송 성공, 상태 코드: " + response.status());
            if (response.body() != null) {
                String body = new String(response.body().asInputStream().readAllBytes());
                System.out.println("응답 내용: " + body);
            }
        } catch (Exception e) {
            System.out.println("메일전송 실패");
            e.printStackTrace();
        }

    }
}
