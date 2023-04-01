package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {
    private final SelenideElement sum = $("[data-test-id='amount'] input");
    private final SelenideElement account = $("[data-test-id='from'] input");
    private final SelenideElement topUpButton = $("[data-test-id=action-transfer].button");

    private SelenideElement cancel = $("[data-test-id='action-cancel']");

    public void transferFromCardToCard(int amount, DataHelper.CardInfo from) {
        sum.setValue(String.valueOf(amount));
        account.setValue(String.valueOf(from));
        topUpButton.click();
        new DashboardPage();
    }


    public void ErrorWindow() {
        SelenideElement errorNotification = $("[data-test-id = 'error-notification']");
        errorNotification.shouldBe(visible);

    }
}