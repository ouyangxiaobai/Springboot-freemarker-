package com.bysj.base.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


/**
 * @program: base
 * @description: 邮箱表示
 * @author: jshibo
 * @create: 2020-10-27 21:39
 **/
@Service
public class MailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @ResponseBody
    @RequestMapping("/send")
    public String send(String to, String subject, String context) {

        if(to.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"))
        {
            System.out.println("OK");
        }
        else
        {
            return "Err";
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("猿来如此楼道维修管理系统<"+from+">");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
        return "success";
    }









}
