package org.poo.main.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.card.Card;
import org.poo.main.bank.ExchangeRate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.poo.main.bank.ExchangeRate.calculateExchangeRate;
import static org.poo.main.utils.Utils.putTransactionInObject;
import static org.poo.main.utils.Utils.roundToTwoDecimalPlates;


public class ClassicAccount implements Account {
    private String iban;
    protected double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Transactions> transactions = new ArrayList<>();

    public ClassicAccount(final String iban, final String currency, String accountType) {
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
        balance = 0;
    }

    /**
     *
     * @return account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     *
     * @param accountType type of the account
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     *
     * @return current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance current balance setter
     */
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     *
     * @return credit cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     *
     * @param cards setter for the list of cards
     */
    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     *
     * @return account currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency setter
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return iban
     */
    public String getIban() {
        return iban;
    }

    /**
     *
     * @param iban setter
     */
    public void setIban(final String iban) {
        this.iban = iban;
    }

    /**
     *
     * @return transactions
     */
    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions setter
     */
    public void setTransactions(final ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    /**
     *
     * @return minimum balance
     */
    public double getMinBalance() {
        return minBalance;
    }

    /**
     *
     * @param minBalance setter
     */
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     *
     * @param command command from input
     * @param amount to be sent
     * @param recieverAccount the account of the reciever
     * @param timestamp current timestamp
     * @param description payment description
     * @param exchangeRates bank's exchange rates
     */
    public void sendMoney(final String command, final double amount, final Account recieverAccount,
                          final int timestamp, final String description,
                          final List<ExchangeRate> exchangeRates) {
        Transactions newTransaction, recieverTransaction;
        ExchangeRate exchangeRate = calculateExchangeRate(this,
                recieverAccount.getCurrency(), exchangeRates);
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        if (balance < amount) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp,
                    "Insufficient funds").build();
            transactions.add(newTransaction);
            return;
        }
        newTransaction =
                new Transactions.TransactionsBuilder(timestamp, description).setSenderIban(iban)
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount + " " + currency)
                        .setTransferType("sent").build();
        balance = balance - amount;
        recieverAccount.setBalance(recieverAccount.getBalance() + amount * exchangeRate.getRate());
        transactions.add(newTransaction);
        recieverTransaction =
                new Transactions.TransactionsBuilder(timestamp, description).setSenderIban(iban)
                .setReceiverIban(recieverAccount.getIban()).setAmount(amount
                                * exchangeRate.getRate() + " "
                                + recieverAccount.getCurrency()).
                setTransferType("received").build();
        recieverAccount.getTransactions().add(recieverTransaction);
    }


    /**
     *
     * @param currency      currency for the payment
     * @param amount        for the payment
     * @param people        which contribute at the split
     * @param exchangeRates bank's exchange rates
     * @return
     */
    public boolean checkEnoughForSplit(final String currency, final double amount, final int people,
                                       final List<ExchangeRate> exchangeRates) {
        double amountAfterDivide = amount / people;
        ExchangeRate exchangeRate = calculateExchangeRate(this, currency, exchangeRates);
        return !(balance < amountAfterDivide * 1 / exchangeRate.getRate());
    }

    /**
     *
     * @param currency         for the payment
     * @param amount           for the payment
     * @param people           which contribute at the split
     * @param exchangeRates    bank's exchange rates
     * @param timestamp        current timestamp
     * @param involvedAccounts which contribute at the split
     */
    public void splitPayment(final String currency, final double amount, final int people,
                             final List<ExchangeRate> exchangeRates, final int timestamp,
                             final List<String> involvedAccounts) {
        double amountAfterDivide = amount / people;
        ExchangeRate exchangeRate = calculateExchangeRate(this, currency, exchangeRates);
        exchangeRate.setRate((exchangeRate.getRate()));
        balance = balance - amountAfterDivide * 1 / exchangeRate.getRate();
        Transactions newTransaction;
        if (amount % 1 == 0) {
            newTransaction = new Transactions.TransactionsBuilder(timestamp,
                    "Split payment of "
                            + (amount) + "0 " + currency).setAmountOnline(amountAfterDivide)
                    .setCurrency(currency).setInvolvedAccounts(involvedAccounts).build();
        } else {
            newTransaction = new Transactions.TransactionsBuilder(timestamp,
                    "Split payment of "
                            + (amount) + " " + currency).setAmountOnline(amountAfterDivide).
                    setCurrency(currency).setInvolvedAccounts(involvedAccounts).build();
        }
        transactions.add(newTransaction);
    }

    /**
     *
     * @param interestRate changed interest rate
     * @param timestamp    current timestamp
     * @return
     */
    public boolean changeInterestRate(final double interestRate, final int timestamp) {
        return false;
    }

    /**
     *
     * @return false
     */
    public boolean addInterest() {
        return false;
    }

    /**
     *
     * @param startTimeStamp start point
     * @param endTimeStamp   end point
     * @param timestamp      current timestamp
     * @return
     */
    public ObjectNode makeReport(final int startTimeStamp, final int endTimeStamp,
                                 final int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("IBAN", iban);
        objectNode.put("balance", balance);
        objectNode.put("currency", currency);
        ArrayNode arrayNode = new ObjectMapper().createArrayNode();
        for (Transactions transactions : transactions) {
            if (transactions.getTimestamp() >= startTimeStamp && transactions.getTimestamp()
                    <= endTimeStamp) {
                ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                putTransactionInObject(objectNode1, transactions);
                arrayNode.add(objectNode1);
            }
        }
        objectNode.put("transactions", arrayNode);
        return objectNode;
    }

    /**
     *
     * @param startTimeStamp start point
     * @param endTimeStamp   end point
     * @param timestamp      current timestamp
     * @return
     */
    public ObjectNode makeSpendingsReport(final int startTimeStamp, final int endTimeStamp,
                                          final int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("IBAN", iban);
        objectNode.put("balance", balance);
        objectNode.put("currency", currency);
        ArrayNode arrayNode = new ObjectMapper().createArrayNode();
        Map<String, Double> commerciants = new HashMap<>();
        List<String> commerciantNames = new ArrayList<>();
        for (Transactions transactions : transactions) {
            if (transactions.getTimestamp() >= startTimeStamp && transactions.getTimestamp()
                    <= endTimeStamp
                    && transactions.getDescription().equals("Card payment")) {
                ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                putTransactionInObject(objectNode1, transactions);
                arrayNode.add(objectNode1);
                if (commerciants.containsKey(transactions.getCurrency())) {
                    double amountSpent = commerciants.get(transactions.getCommerciant());
                    commerciants.replace(transactions.getCommerciant(), amountSpent,
                            amountSpent
                            + transactions.getAmountOnline());
                } else {
                    commerciants.putIfAbsent(transactions.getCommerciant(),
                            transactions.getAmountOnline());
                }
                commerciantNames.add(transactions.getCommerciant());
            }
        }
        ArrayNode arrayNode1 = new ObjectMapper().createArrayNode();
        commerciantNames.sort(new Comparator<String>() {
            public int compare(final String o1, final String o2) {
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
