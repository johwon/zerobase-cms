package com.zerobase.cms.user.client;

import com.zerobase.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url="https://api.mailgun.net/v3/")
public interface MailgunClient {
    @PostMapping("sandboxb072ef3fd20e4dc3a400833c672d701f.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);


}
