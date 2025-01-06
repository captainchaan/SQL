package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerifyPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");

    public VerifyPage() {
        codeField.shouldBe(visible);
    }

    public void verifyErrorNotification(String expectedText){
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public DashboardPage validVerify(String VerifyCode) {
        verify(VerifyCode);
        return new DashboardPage();
    }

    public void verify(String VerifyCode) {
        codeField.setValue(VerifyCode);
        verifyButton.click();
    }
}
