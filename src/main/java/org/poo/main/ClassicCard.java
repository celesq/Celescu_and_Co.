package org.poo.main;

import java.util.ArrayList;
import java.util.Formatter;

import static java.lang.Math.round;
import static org.poo.main.Utils.*;
import static org.poo.utils.Utils.*;

public class ClassicCard implements Card {
    private String number;
    private String status;

    public ClassicCard(String number, String status) {
        this.number = number;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void pay(Account account, Card card, double amount, String currency ,String description, String commerciant,
                    String email, int timestamp, ArrayList<ExchangeRate> exchangeRates) {
        Transactions newTransaction;
        ExchangeRate exchangeRate = calculateExchangeRate(account, currency, exchangeRates);
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        if (account.getBalance() * exchangeRate.getRate() < amount|| card.getStatus().equals("frozen")) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Insufficient funds").build();
            account.getTransactions().add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp, description).
                setAmount_online(amount * 1 / exchangeRate.getRate()).setCommerciant(commerciant).build();
        account.getTransactions().add(newTransaction);
        account.setBalance((account.getBalance() - amount / exchangeRate.getRate()));
    }
}