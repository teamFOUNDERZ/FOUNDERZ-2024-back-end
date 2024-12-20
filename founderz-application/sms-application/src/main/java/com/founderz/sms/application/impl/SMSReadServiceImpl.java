package com.founderz.sms.application.impl;

import com.founderz.common.exception.ServerException;
import com.founderz.common.vo.user.PhoneNumber;
import com.founderz.sms.application.SMSReadService;
import com.founderz.sms.domain.presistence.SmsCertification;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.java_sdk.api.Message;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
class SMSReadServiceImpl implements SMSReadService {
    private final SmsCertification smsCertification;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    private String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 6; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", to);
        params.put("text", "파운더즈 본인확인 인증번호\n[" + randomNum + "]");
        return params;
    }

    // 인증번호 전송하기
    @Override
    public boolean send(PhoneNumber tel) {
        Message coolsms = new Message(apiKey, apiSecret);

        // 랜덤한 인증 번호 생성
        String randomNum = createRandomNumber();

        // 발신 정보 설정
        HashMap<String, String> params = makeParams(tel.phoneNumber(), randomNum);

        try {
            coolsms.send(params);
        } catch (CoolsmsException e) {
            throw new ServerException(e.getMessage());
        }

        smsCertification.createSmsCertification(tel.phoneNumber(), randomNum);

        return false;
    }

    @Override
    public boolean verifySms(PhoneNumber tel, String randomNum) {
        if (isVerify(tel, randomNum)) {
            return false;
        }
        smsCertification.deleteSmsCertification(tel.phoneNumber());

        return true;
    }

    private boolean isVerify(PhoneNumber tel, String randomNum) {
        return !(smsCertification.hasKey(tel.phoneNumber())) &&
                smsCertification.getSmsCertification(tel.phoneNumber())
                        .equals(randomNum);
    }
}
