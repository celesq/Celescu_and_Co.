package org.poo.main;

import java.util.List;

public class Command {
    private String command;
    private String email;
    private String account;
    private String currency;
    private double amount;
    private double minBalance;
    private String target;
    private String description;
    private String cardNumber;
    private String commerciant;
    private int timestamp;
    private int startTimestamp;
    private int endTimestamp;
    private String receiver;
    private String alias;
    private String accountType;
    private double interestRate;
    private List<String> accounts;

    public Command (CommandBuilder builder) {
        this.command = builder.getCommand();
        this.email = builder.getEmail();
        this.account = builder.getAccount();
        this.currency = builder.getCurrency();
        this.amount = builder.getAmount();
        this.minBalance = builder.getMinBalance();
        this.target = builder.getTarget();
        this.description = builder.getDescription();
        this.cardNumber = builder.getCardNumber();
        this.commerciant = builder.getCommerciant();
        this.timestamp = builder.getTimestamp();
        this.startTimestamp = builder.getStartTimestamp();
        this.endTimestamp = builder.getEndTimestamp();
        this.receiver = builder.getReceiver();
        this.alias = builder.getAlias();
        this.accountType = builder.getAccountType();
        this.interestRate = builder.getInterestRate();
        this.accounts = builder.getAccounts();
    }
}
