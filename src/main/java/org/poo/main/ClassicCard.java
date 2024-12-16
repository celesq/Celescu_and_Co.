package org.poo.main;

import java.util.ArrayList;
import java.util.List;

import static org.poo.main.ExchangeRate.calculateExchangeRate;
import static org.poo.main.Utils.roundToTwoDecimalPlates;

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
                    String email, int timestamp, List<ExchangeRate> exchangeRates) {
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
        card.update(account, timestamp);
    }

    @Override
    public void update(Account checkedAccount, int timestamp) {
        if (checkedAccount.getBalance() <= checkedAccount.getMinBalance()) {
            setStatus("frozen");
            Transactions transaction = new Transactions.TransactionsBuilder(timestamp
                    , "You have reached the minimum amount of funds, the card will be frozen").build();
            checkedAccount.getTransactions().add(transaction);
        } else if (checkedAccount.getBalance() - checkedAccount.getMinBalance() <= 30) {
            setStatus("warning");
            Transactions transaction = new Transactions.TransactionsBuilder(timestamp
                    , "Card warning").build();
            checkedAccount.getTransactions().add(transaction);
        }
    }
}