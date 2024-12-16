package org.poo.main;

import java.util.ArrayList;
import java.util.List;

public class Transactions {
    private int timestamp;
    private String description;
    private String amount;
    private String senderIban;
    private String receiverIban;
    private String transferType;
    private String commerciant;
    private String account;
    private String card;
    private String cardHolder;
    private double amount_online;
    private String currency;
    private List<String> involvedAccounts = new ArrayList<>();
    private String error;

    public Transactions(TransactionsBuilder builder) {
        this.timestamp = builder.timestamp;
        this.description = builder.description;
        this.amount = builder.amount;
        this.senderIban = builder.senderIban;
        this.receiverIban = builder.receiverIban;
        this.transferType = builder.transferType;
        this.commerciant = builder.commerciant;
        this.account = builder.account;
        this.card = builder.card;
        this.cardHolder = builder.cardHolder;
        this.amount_online = builder.amount_online;
        this.currency = builder.currency;
        this.involvedAccounts = builder.involvedAccounts;
        this.error = builder.error;
    }

    static class TransactionsBuilder {
        private int timestamp = 0;
        private String description = null;
        private String amount = null;
        private String senderIban = null;
        private String receiverIban = null;
        private String transferType = null;
        private String commerciant = null;
        private String account = null;
        private String card = null;
        private String cardHolder = null;
        private double amount_online = 0;
        private String currency = null;
        private List<String> involvedAccounts = null;
        private String error = null;

        public TransactionsBuilder (int timestamp, String description) {
            this.timestamp = timestamp;
            this.description = description;
        }

        public TransactionsBuilder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public TransactionsBuilder setSenderIban(String senderIban) {
            this.senderIban = senderIban;
            return this;
        }

        public TransactionsBuilder setReceiverIban(String receiverIban) {
            this.receiverIban = receiverIban;
            return this;
        }

        public TransactionsBuilder setTransferType(String transferType) {
            this.transferType = transferType;
            return this;
        }

        public TransactionsBuilder setCommerciant(String commerciant) {
            this.commerciant = commerciant;
            return this;
        }

        public TransactionsBuilder setAccount(String account) {
            this.account = account;
            return this;
        }

        public TransactionsBuilder setCard(String card) {
            this.card = card;
            return this;
        }

        public TransactionsBuilder setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public TransactionsBuilder setAmount_online(double amount_online) {
            this.amount_online = amount_online;
            return this;
        }

        public TransactionsBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionsBuilder setInvolvedAccounts(List<String> involvedAccounts) {
            this.involvedAccounts = involvedAccounts;
            return this;
        }

        public TransactionsBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public Transactions build() {
            return new Transactions(this);
        }


        public String getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReceiverIban() {
            return receiverIban;
        }

        public String getSenderIban() {
            return senderIban;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getTransferType() {
            return transferType;
        }

        public String getCommerciant() {
            return commerciant;
        }

        public String getAccount() {
            return account;
        }

        public String getCard() {
            return card;
        }

        public String getCardHolder() {
            return cardHolder;
        }

        public double getAmount_online() {
            return amount_online;
        }

        public String getCurrency() {
            return currency;
        }

        public List<String> getInvolvedAccounts() {
            return involvedAccounts;
        }

        public String getError() {
            return error;
        }
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiverIban() {
        return receiverIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public String getSenderIban() {
        return senderIban;
    }

    public void setSenderIban(String senderIban) {
        this.senderIban = senderIban;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public void setCommerciant(String commerciant) {
        this.commerciant = commerciant;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public double getAmount_online() {
        return amount_online;
    }

    public void setAmount_online(double amount_online) {
        this.amount_online = amount_online;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    public void setInvolvedAccounts(List<String> involvedAccounts) {
        this.involvedAccounts = involvedAccounts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
