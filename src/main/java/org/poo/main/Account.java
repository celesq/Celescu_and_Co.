package org.poo.main;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Account {

    public String getAccountType();

    public void setAccountType(String accountType);

    public double getBalance();

    public void setBalance(double balance);

    public ArrayList<Card> getCards();

    public void setCards(ArrayList<Card> cards);

    public String getCurrency();

    public void setCurrency(String currency);

    public String getIban();

    public void setIban(String iban);

    public ArrayList<Transactions> getTransactions();

    public void setTransactions(ArrayList<Transactions> transactions);

    public double getMinBalance();

    public void setMinBalance(double minBalance);

    public void sendMoney(String command, double amount, Account recieverAccount, int timestamp, String description,
                          ArrayList<ExchangeRate> exchangeRates);

    public boolean checkEnoughForSplit(String currency, double amount, int people,
                                       ArrayList<ExchangeRate> exchangeRates);

    public void splitPayment(String currency, double amount, int people,
                             ArrayList<ExchangeRate> exchangeRates, int timestamp, List<String> involvedAccounts);


    public boolean changeInterestRate(double interestRate);

    public boolean addInterest();

    public ObjectNode makeReport(int startTimeStamp, int endTimeStamp, int timestamp);

    public ObjectNode makeSpendingsReport(int startTimeStamp, int endTimeStamp, int timestamp);
}
