package org.poo.main;

import java.util.ArrayList;
import java.util.Formatter;

import static java.lang.Math.round;
import static org.poo.utils.Utils.*;;

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
        if (account.getBalance() * exchangeRate.getRate() < amount|| card.getStatus().equals("frozen")) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Insufficient funds").build();
            account.getTransactions().add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp, description).
                setAmount_online(amount * 1 / exchangeRate.getRate()).setCommerciant(commerciant).build();
        account.getTransactions().add(newTransaction);
        account.setBalance(account.getBalance() - amount / exchangeRate.getRate());
    }

    public ExchangeRate calculateExchangeRate(Account account, String currency, ArrayList<ExchangeRate> exchangeRates) {
        ExchangeRate exchangeRate = new ExchangeRate(account.getCurrency(), currency, 1);
        if (account.getCurrency().equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(currency) && exchangeRate1.getTo().equals(account.getCurrency())) {
                String aux = exchangeRate.getFrom();
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(aux);
                exchangeRate.setRate(1 / exchangeRate1.getRate());
                return exchangeRate;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) && exchangeRate1.getTo().equals(currency)) {
                return exchangeRate1;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) || exchangeRate1.getTo().equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getFrom());
                exchangeRate.setTo(exchangeRate1.getTo());
                exchangeRate.setRate(exchangeRate.getRate() * exchangeRate1.getRate());
            }
        }
        if (exchangeRate.getFrom().equals(account.getCurrency()) && exchangeRate.getTo().equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getTo().equals(account.getCurrency()) || exchangeRate1.getFrom().equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(exchangeRate1.getFrom());
                exchangeRate.setRate(exchangeRate.getRate() * 1 / exchangeRate1.getRate());
            }
        }
        return exchangeRate;
    }
}