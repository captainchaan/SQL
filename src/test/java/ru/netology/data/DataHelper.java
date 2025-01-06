package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


import java.util.Locale;

public class DataHelper {

    private static final Faker faker= new Faker(new Locale("en"));

    private DataHelper(){
    }

    public static AuthInfo getAuthInfoWithTestData(){
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String generateRandomLogin(){

        return faker.name().username();
    }

    public static String generateRandomPassword(){
        return faker.internet().password();
    }

    public static AuthInfo generateRandomUser(){
        return new AuthInfo(generateRandomLogin(),generateRandomPassword());
    }


    public static VerificationCode generateRandomVerifyCode(){
        return new VerificationCode(faker.numerify("######"));
    }

    public static AuthInfo invalidPassword() {
        return new AuthInfo("petya", generateRandomPassword());
    }




    @Value
    public static class AuthInfo{
        String login;
        String password;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode{
        String code;
    }
}
