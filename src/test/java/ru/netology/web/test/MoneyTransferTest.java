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
        int amount = 6000;

        var transfer = new DashboardPage().firstCardButton();
        transfer.transferFromCardToCard(amount, getSecondCard());
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

        var transfer = new DashboardPage().secondCardButton();
        transfer.transferFromCardToCard(amount, getFirstCard());
        var firstCardBalanceResult = firstCardBalanceStart - amount;
        var secondCardBalanceResult = secondCardBalanceStart + amount;

        assertEquals(firstCardBalanceResult, dashboardpage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardpage.getSecondCardBalance());
    }

    @Test
    @DisplayName("Should not transfer money if the amount is more on the balance")
    public void shouldNotTransferMoneyIfAmountMoreBalance() {
        int amount = 20000;

        var transfer = new DashboardPage().firstCardButton();
        transfer.transferFromCardToCard(amount, getThirdCard());
        var topUpPage = new TopUpPage();
        topUpPage.errorWindow();

    }

}




















