package ru.netology.web.test;

import com.codeborne.selenide.Configuration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;


public class MoneyTransferTest {
    DashboardPage dashboardpage;

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardpage = verificationPage.validVerify(verificationCode);

    }

    @Test
    @DisplayName("Replenishment of the first card account")
    public void shouldReplenishedFirstCard() {
        var firstCardBalanceStart = dashboardpage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardpage.getSecondCardBalance();
        int amount = 5000;


        dashboardpage.firstCardButton().transferFromCardToCard(amount, getSecondCard());
        var firstCardBalanceResult = firstCardBalanceStart + amount;
        var secondCardBalanceResult = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceResult, dashboardpage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardpage.getSecondCardBalance());
    }


    @Test
    @DisplayName("Replenishment of the second card account")
    public void shouldReplenishedSecondCard() {
        var firstCardBalanceStart = dashboardpage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardpage.getSecondCardBalance();
        int amount = 5000;


        dashboardpage.secondCardButton().transferFromCardToCard(amount, getFirstCard());
        var firstCardBalanceResult = firstCardBalanceStart - amount;
        var secondCardBalanceResult = secondCardBalanceStart + amount;

        assertEquals(firstCardBalanceResult, dashboardpage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardpage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Should not transfer money if the amount is more the balance")
    public void shouldNotTransferMoneyIfAmountMoreBalance() {
        var firstCardBalanceStart = dashboardpage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardpage.getSecondCardBalance();
        int amount = 20000;


        var transferPage = dashboardpage.firstCardButton();
        transferPage.makeTransfer(amount, getSecondCard());
        transferPage.errorWindow("Выполнена попытка перевода суммы,превышающий остаток на карте списания!");

        var firstCardBalanceActual = dashboardpage.getFirstCardBalance();
        var secondCardBalanceActual = dashboardpage.getSecondCardBalance();


        assertEquals(firstCardBalanceStart, firstCardBalanceActual);
        assertEquals(secondCardBalanceStart, secondCardBalanceActual);
    }

}




















