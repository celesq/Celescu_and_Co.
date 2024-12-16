package org.poo.main;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.poo.main.Utils.calculateExchangeRate;
import static org.poo.main.Utils.roundToTwoDecimalPlates;


public class ClassicAccount implements Account {
    private String Iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Transactions> transactions = new ArrayList<>();

    public ClassicAccount(String Iban, double balance, String currency, String accountType) {
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
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        if (balance < amount) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Insufficient funds").build();
            transactions.add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp, description).setSenderIban(Iban)
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount + " " + currency).setTransferType("sent").build();
        balance = balance - amount;
        balance = roundToTwoDecimalPlates(balance);
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
        balance = roundToTwoDecimalPlates(balance);
        Transactions newTransaction;
        if (amount * people % 1 == 0) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of " +
                    roundToTwoDecimalPlates(amount * people) + "0 " + currency).
                    setAmount_online(amount).setCurrency(currency).setInvolvedAccounts(involvedAccounts) .build();
        } else {
            newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of " +
                    roundToTwoDecimalPlates(amount * people) + " " + currency).setAmount_online(amount).
                    setCurrency(currency).setInvolvedAccounts(involvedAccounts).build();
        }
        transactions.add(newTransaction);
    }

    public boolean changeInterestRate(double interestRate) {
        return false;
    }

    public boolean addInterest() {
        return false;
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
        objectNode.put("IBAN", Iban);
        objectNode.put("balance", balance);
        objectNode.put("currency", currency);
        ArrayNode arrayNode = new ObjectMapper().createArrayNode();
        Map<String, Double> commerciants = new HashMap<>();
        List<String> commerciantNames = new ArrayList<>();
        for (Transactions transactions : transactions) {
            if (transactions.getTimestamp() >= startTimeStamp && transactions.getTimestamp() <= endTimeStamp
                    && transactions.getDescription().equals("Card payment")) {
                ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                Output.putTransactionInObject(objectNode1, transactions);
                arrayNode.add(objectNode1);
                if (commerciants.containsKey(transactions.getCurrency())) {
                    double amountSpent = commerciants.get(transactions.getCommerciant());
                    commerciants.replace(transactions.getCommerciant(), amountSpent, amountSpent
                            + transactions.getAmount_online());
                } else {
                    commerciants.putIfAbsent(transactions.getCommerciant(), transactions.getAmount_online());
                }
                commerciantNames.add(transactions.getCommerciant());
            }
        }
        ArrayNode arrayNode1 = new ObjectMapper().createArrayNode();
        commerciantNames.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String commerciantName : commerciantNames) {
            ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
            objectNode1.put("commerciant", commerciantName);
            objectNode1.put("total", commerciants.get(commerciantName));
            arrayNode1.add(objectNode1);
        }
        objectNode.put("transactions", arrayNode);
        objectNode.put("commerciants", arrayNode1);
        return objectNode;
    }
}