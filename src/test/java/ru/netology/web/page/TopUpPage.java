package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {
    private final SelenideElement sum = $("[data-test-id='amount'] input");
    private final SelenideElement account = $("[data-test-id='from'] input");
    private final SelenideElement topUpButton = $("[data-test-id=action-transfer].button");

    private final SelenideElement errorNotification = $("[data-test-id = 'error-notification']");
    private SelenideElement cancel = $("[data-test-id='action-cancel']");

    public TopUpPage() {
        topUpButton.shouldBe(visible);
    }

    public DashboardPage transferFromCardToCard(int amount, DataHelper.CardInfo from) {
        sum.setValue(String.valueOf(amount));
        account.setValue(String.valueOf(from));
        topUpButton.click();
        return new DashboardPage();
    }

    public void makeTransfer(int amount, DataHelper.CardInfo from) {
        sum.setValue(String.valueOf(amount));
        account.setValue(String.valueOf(from));
        topUpButton.click();
    }


    public void errorWindow(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);

    }
}