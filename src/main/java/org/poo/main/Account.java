package org.poo.main;

import java.util.ArrayList;

public class Account {
    private String Iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Transactions> transactions = new ArrayList<>();

    public Account(String Iban, double balance, String currency, String accountType) {
        this.Iban = Iban;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return Iban;
    }

    public void setIban(String iban) {
        Iban = iban;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public void sendMoney(String command, double amount, Account recieverAccount, int timestamp, String description,
                          ArrayList<ExchangeRate> exchangeRates) {
        Transactions newTransaction, recieverTransaction;
        ExchangeRate exchangeRate = calculateExchangeRate(this, recieverAccount.getCurrency(), exchangeRates);
        if (balance * exchangeRate.getRate() < amount) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Insufficient funds").build();
            transactions.add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp, description).setSenderIban(Iban)
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount + " " + currency).setTransferType("sent").build();
        balance = balance - amount;
        recieverAccount.setBalance(recieverAccount.getBalance() + amount * exchangeRate.getRate());
        transactions.add(newTransaction);
        recieverTransaction = new Transactions.TransactionsBuilder(timestamp, description).setSenderIban(Iban)
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount * exchangeRate.getRate() + " " + currency).
                setTransferType("received").build();
        recieverAccount.getTransactions().add(recieverTransaction);
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
        return exchangeRate;
    }
}
