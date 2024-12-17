package org.poo.main;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public interface Account {

    /**
     * @return the type of the account
     */
    String getAccountType();

    /**
     * @param accountType type of the account
     */
    void setAccountType(String accountType);

    /**
     *
     * @return current balance
     */
    double getBalance();

    /**
     *
     * @param balance current balance setter
     */
    void setBalance(double balance);

    /**
     *
     * @return the list of credit cards
     */
    ArrayList<Card> getCards();

    /**
     *
     * @param cards setter for the list of cards
     */
    void setCards(ArrayList<Card> cards);

    /**
     *
     * @return account currency
     */
    String getCurrency();

    /**
     *
     * @param currency setter
     */
    void setCurrency(String currency);

    /**
     *
     * @return account IBAN
     */
    String getIban();

    /**
     *
     * @param iban setter
     */
    void setIban(String iban);

    /**
     *
     * @return the list of transactions
     */
    ArrayList<Transactions> getTransactions();

    /**
     * ]
     * @param transactions setter
     */
    void setTransactions(ArrayList<Transactions> transactions);

    /**
     *
     * @return minimum balance
     */
    double getMinBalance();

    /**
     *
     * @param minBalance setter
     */
    void setMinBalance(double minBalance);

    /**
     *
     * @param command command from input
     * @param amount to be sent
     * @param recieverAccount the account of the reciever
     * @param timestamp current timestamp
     * @param description payment description
     * @param exchangeRates bank's exchange rates
     */
    void sendMoney(String command, double amount, Account recieverAccount, int timestamp,
                   String description, List<ExchangeRate> exchangeRates);

    /**
     * @param currency      currency for the payment
     * @param amount        for the payment
     * @param people        which contribute at the split
     * @param exchangeRates bank's exchange rates
     * @return true if the accounts have enough money to split the payment
     */
    boolean checkEnoughForSplit(String currency, double amount, int people,
                                List<ExchangeRate> exchangeRates);

    /**
     * @param currency         for the payment
     * @param amount           for the payment
     * @param people           which contribute at the split
     * @param exchangeRates    bank's exchange rates
     * @param timestamp        current timestamp
     * @param involvedAccounts which contribute at the split
     */
    void splitPayment(String currency, double amount, int people, List<ExchangeRate> exchangeRates,
                      int timestamp, List<String> involvedAccounts);


    /**
     * @param interestRate changed interest rate
     * @param timestamp    current timestamp
     * @return true if it is a savings account, false otherwise
     */
    boolean changeInterestRate(double interestRate, int timestamp);

    /**
     *
     * @return true if it is a savings account, false otherwise
     */
    boolean addInterest();

    /**
     * @param startTimeStamp start point
     * @param endTimeStamp   end point
     * @param timestamp      current timestamp
     * @return the object node
     */
    ObjectNode makeReport(int startTimeStamp, int endTimeStamp, int timestamp);

    /**
     * @param startTimeStamp start point
     * @param endTimeStamp   end point
     * @param timestamp      current timestamp
     * @return the object node
     */
    ObjectNode makeSpendingsReport(int startTimeStamp, int endTimeStamp, int timestamp);

}
