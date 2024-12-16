package org.poo.main;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.poo.main.Utils.calculateExchangeRate;
import static org.poo.main.Utils.roundToTwoDecimalPlates;

public class SavingsAccount implements Account {

    private String Iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private double interestRate;

    public SavingsAccount(String Iban, double balance, String currency, String accountType, double interestRate) {
        this.Iban = Iban;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void sendMoney(String command, double amount, Account recieverAccount, int timestamp, String description,
                          ArrayList<ExchangeRate> exchangeRates) {
        Transactions newTransaction, recieverTransaction;
        ExchangeRate exchangeRate = calculateExchangeRate(this, recieverAccount.getCurrency(), exchangeRates);
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        if (balance < amount) {
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
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount * exchangeRate.getRate() + " " +
                        recieverAccount.getCurrency()).
                setTransferType("received").build();
        recieverAccount.getTransactions().add(recieverTransaction);
    }

    public boolean checkEnoughForSplit(String currency, double amount, int people,
                                       ArrayList<ExchangeRate> exchangeRates) {
        amount = amount / people;
        ExchangeRate exchangeRate = calculateExchangeRate(this, currency, exchangeRates);
        if (balance < amount * 1 / exchangeRate.getRate()) {
            return false;
        }
        return true;
    }

    public void splitPayment(String currency, double amount, int people,
                             ArrayList<ExchangeRate> exchangeRates, int timestamp, List<String> involvedAccounts) {
        amount = amount / people;
        ExchangeRate exchangeRate = calculateExchangeRate(this, currency, exchangeRates);
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        balance = balance - amount * 1 / exchangeRate.getRate();
        Transactions newTransaction;
        if (amount * people % 1 == 0) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of " +
                    (amount * people) + "0 " + currency).
                    setAmount_online(amount).setCurrency(currency).setInvolvedAccounts(involvedAccounts) .build();
        } else {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of " +
                    (amount * people) + " " + currency).setAmount_online(amount).
                    setCurrency(currency).setInvolvedAccounts(involvedAccounts).build();
        }
        transactions.add(newTransaction);
    }


    public boolean changeInterestRate(double interestRate, int timestamp) {
        this.interestRate = interestRate;
        Transactions newTransaction = new Transactions.TransactionsBuilder(timestamp,
                "Interest rate of the account changed to " + interestRate).build();
        getTransactions().add(newTransaction);
        return true;
    }

    public boolean addInterest() {
        balance = balance + balance * interestRate;
        return true;
    }

    public ObjectNode makeReport(int startTimeStamp, int endTimeStamp, int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("IBAN", Iban);
        objectNode.put("balance", balance);
        objectNode.put("currency", currency);
        ArrayNode arrayNode = new ObjectMapper().createArrayNode();
        for (Transactions transactions : transactions) {
            if (transactions.getTimestamp() >= startTimeStamp && transactions.getTimestamp() <= endTimeStamp) {
                ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                Output.putTransactionInObject(objectNode1, transactions);
                arrayNode.add(objectNode1);
            }
        }
        objectNode.put("transactions", arrayNode);
        return objectNode;
    }

    public ObjectNode makeSpendingsReport(int startTimeStamp, int endTimeStamp, int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("error", "This kind of report is not supported for a saving account");
        return objectNode;
    }
}
