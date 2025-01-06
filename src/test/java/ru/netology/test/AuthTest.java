package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDB;

public class AuthTest {

    LoginPage loginPage;
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfoWithTestData();


    @AfterAll
    static void tearDownAll() {
        cleanDB();
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }


    @Test
    @DisplayName("Позитивный тест, вход с валидными данными и кодом")
    void shouldSuccessfulLogin() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Вход с невалидными данными")
    void shouldInvalidUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    @DisplayName("Вход с невалидным кодом")
    void shouldInvalidCode() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.generateRandomVerifyCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }



    @Test
    @DisplayName("Ошибка при 3х невалидных введенных паролей")
    void shouldBlockPageWithTreeIncorrectPasswords() {
        for (int i = 0; i < 4; i++) {
            var authInfo = DataHelper.invalidPassword();
            loginPage.login(authInfo);
            if (i == 3) {
                loginPage.verifyErrorNotification("Ошибка! \nПользователь заблокирован");
            } else {
                loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
            }
            loginPage.clean();
        }
    }


}

