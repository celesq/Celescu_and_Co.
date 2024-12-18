package org.poo.card;

import org.poo.bank.ExchangeRate;
import org.poo.account.Account;
import org.poo.account.BalanceObserver;

import java.util.List;

public interface Card extends BalanceObserver {

    /**
     * @return card number
     */
    String getNumber();

    /**
     * @param number setter
     */
    void setNumber(String number);

    /**
     *
     * @return card status
     */
    String getStatus();

    /**
     *
     * @param status for the card
     */
    void setStatus(String status);

    /**
     *
     * @param account account which pays
     * @param card used card
     * @param amount for payment
     * @param currency used
     * @param description for payment
     * @param commerciant for payment
     * @param email user's
     * @param timestamp current timestamp
     * @param exchangeRates bank's exchange rates
     */
    void pay(Account account, Card card, double amount, String currency, String description,
             String commerciant, String email, int timestamp, List<ExchangeRate> exchangeRates);
}
