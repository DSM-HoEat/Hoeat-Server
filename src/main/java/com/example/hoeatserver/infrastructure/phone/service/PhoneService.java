package com.example.hoeatserver.infrastructure.phone.service;

import com.example.hoeatserver.infrastructure.phone.dto.request.PhoneRequest;
import com.example.hoeatserver.infrastructure.phone.entity.PhoneCode;
import com.example.hoeatserver.infrastructure.phone.repository.CodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class PhoneService{

    private final CodeRepository codeRepository;

    @Value("${spring.coolsms.devHee.apikey}")
<<<<<<< Updated upstream
    private final String apiKey;


    @Value("${spring.coolsms.devHee.apisecret}")
    private final String apiSecret;

    @Value("${spring.coolsms.phone}")
    private final String myPhone;

    @Value("${spring.coolsms.live.code}")
    private final Long codeTime;
=======
    private String apiKey;


    @Value("${spring.coolsms.devHee.apisecret}")
    private String apiSecret;

    @Value("${spring.coolsms.phone}")
    private String myPhone;

    @Value("${spring.coolsms.live.code}")
    private Long codeTime;
>>>>>>> Stashed changes


    @Transactional
    public void execute(PhoneRequest request) {

        Message message = new Message();

        message.setFrom(myPhone);
        message.setTo(request.getPhoneNumber());
        message.setText("[호잇] 인증번호 ["+randomCode(request)+"]를 입력해주세요.");

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
    }

    private String randomCode(PhoneRequest request){

        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        codeRepository.save(
                new PhoneCode(
                        request.getPhoneNumber(),
                        sb.toString(),
                        codeTime
                )
        );

        return sb.toString();
    }
}
