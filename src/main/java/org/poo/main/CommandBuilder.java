package org.poo.main;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    private String command;
    private String email = null;
    private String account = null;
    private String currency = null;
    private double amount = 0.0;
    private double minBalance = 0.0;
    private String target = null;
    private String description = null;
    private String cardNumber = null;
    private String commerciant = null;
    private int timestamp = 0;
    private int startTimestamp = 0;
    private int endTimestamp = 0;
    private String receiver = null;
    private String alias = null;
    private String accountType = null;
    private double interestRate = 0.0;
    private List<String> accounts = new ArrayList<>();

    public CommandBuilder(String command) {
        this.command = command;
    }

    public CommandBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CommandBuilder withAccount(String account) {
        this.account = account;
        return this;
    }

    public CommandBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public CommandBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public CommandBuilder withMinBalance(double minBalance) {
        this.minBalance = minBalance;
        return this;
    }

    public CommandBuilder withTarget(String target) {
        this.target = target;
        return this;
    }

    public CommandBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public CommandBuilder withCommerciant(String commerciant) {
        this.commerciant = commerciant;
        return this;
    }

    public CommandBuilder withTimestamp(int timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public CommandBuilder withStartTimestamp(int startTimestamp) {
        this.startTimestamp = startTimestamp;
        return this;
    }

    public CommandBuilder withEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
        return this;
    }

    public CommandBuilder withReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public CommandBuilder withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public CommandBuilder withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public CommandBuilder withInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public CommandBuilder withAccounts(List<String> accounts) {
        this.accounts = accounts;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public void setCommerciant(String commerciant) {
        this.commerciant = commerciant;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(int endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(int startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Command build() {
        return new Command(this);
    }
}
