package com.example.liangge.indiana.model.user;

/**
 * 验证码实体
 * Created by baoxing on 2015/12/26.
 */
public class VerticationCodeEntity {

    private String verificationCode;


    public VerticationCodeEntity() {
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public String toString() {
        return "VerticationCodeEntity{" +
                "verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
