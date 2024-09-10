package com.sabujaks.irs.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender emailSender;
    SimpleMailMessage message;

    public String sendEmail(List<String> emails){
        String uuid = UUID.randomUUID().toString();
        for(String email : emails) {
            message = new SimpleMailMessage();
            message.setTo(email);

            message.setSubject("[면접 URL 안내] 면접 URL 안내드립니다.");

            System.out.println("uuid: " + uuid);
//        message.setText("가입을 완료하시려면 아래 링크를 클릭해주세요."
//                + "http://3.36.126.48:8080/user/active?email=" + request.getEmail()+"&uuid="+uuid);  //Todo

            message.setText("면접 당일 안내드린 링크로 접속해주시기 바랍니다.\n" + uuid);

            // 이메일 전송
            emailSender.send(message);
        }



        return uuid;
    }
}
