package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [role='button']");
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [role='button']");


    private static final ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public TopUpPage firstCardButton() {
        firstCard.click();
        return new TopUpPage();
    }

    public TopUpPage secondCardButton() {
        secondCard.click();
        return new TopUpPage();
    }

    public int getFirstCardBalance() {
        var balance = cards.first().text();
        return extractBalance(balance);
    }

    public int getSecondCardBalance() {
        var balance = cards.last().text();
        return extractBalance(balance);
    }

    private int extractBalance(String balance) {
        var start = balance.indexOf(balanceStart);
        var finish = balance.indexOf(balanceFinish);
        var value = balance.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}