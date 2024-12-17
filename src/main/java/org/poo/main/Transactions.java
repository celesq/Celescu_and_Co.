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
    private double amountOnline;
    private String currency;
    private List<String> involvedAccounts = new ArrayList<>();
    private String error;

    public Transactions(final TransactionsBuilder builder) {
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
        this.amountOnline = builder.amountOnline;
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
        private double amountOnline = 0;
        private String currency = null;
        private List<String> involvedAccounts = null;
        private String error = null;

        public TransactionsBuilder(final int timestamp, final String description) {
            this.timestamp = timestamp;
            this.description = description;
        }

        /**
         *
         * @param amount sets amount
         * @return builder object
         */
        public TransactionsBuilder setAmount(final String amount) {
            this.amount = amount;
            return this;
        }

        /**
         *
         * @param senderIban sets sender IBAN
         * @return builder object
         */
        public TransactionsBuilder setSenderIban(final String senderIban) {
            this.senderIban = senderIban;
            return this;
        }

        /**
         *
         * @param receiverIban
         * @return builder object
         */
        public TransactionsBuilder setReceiverIban(final String receiverIban) {
            this.receiverIban = receiverIban;
            return this;
        }

        /**
         *
         * sets @param transferType
         * @return builder object
         */
        public TransactionsBuilder setTransferType(final String transferType) {
            this.transferType = transferType;
            return this;
        }

        /**
         *
         * sets @param commerciant
         * @return builder object
         */
        public TransactionsBuilder setCommerciant(final String commerciant) {
            this.commerciant = commerciant;
            return this;
        }

        /**
         *
         * sets @param account
         * @return builder object
         */
        public TransactionsBuilder setAccount(final String account) {
            this.account = account;
            return this;
        }

        /**
         *
         * sets @param card
         * @return builder object
         */
        public TransactionsBuilder setCard(final String card) {
            this.card = card;
            return this;
        }

        /**
         *
         * sets @param cardHolder
         * @return builder object
         */
        public TransactionsBuilder setCardHolder(final String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        /**
         *
         * sets @param amount_online
         * @return builder object
         */
        public TransactionsBuilder setAmountOnline(final double amountOnline) {
            this.amountOnline = amountOnline;
            return this;
        }

        /**
         *
         * sets @param currency
         * @return builder object
         */
        public TransactionsBuilder setCurrency(final String currency) {
            this.currency = currency;
            return this;
        }

        /**
         *
         * sets @param involvedAccounts
         * @return builder object
         */
        public TransactionsBuilder setInvolvedAccounts(final List<String> involvedAccounts) {
            this.involvedAccounts = involvedAccounts;
            return this;
        }

        /**
         *
         * sets @param error
         * @return builder object
         */
        public TransactionsBuilder setError(final String error) {
            this.error = error;
            return this;
        }

        /**
         *
         * @return transactions
         */
        public Transactions build() {
            return new Transactions(this);
        }

        /**
         *
         * @return amount
         */
        public String getAmount() {
            return amount;
        }

        /**
         *
         * @return description
         */
        public String getDescription() {
            return description;
        }

        /**
         *
         * sets @param description
         */
        public void setDescription(final String description) {
            this.description = description;
        }

        /**
         *
         * @return receiver's iban
         */
        public String getReceiverIban() {
            return receiverIban;
        }

        /**
         *
         * @return sender's iban
         */
        public String getSenderIban() {
            return senderIban;
        }

        /**
         *
         * @return timestamp
         */
        public int getTimestamp() {
            return timestamp;
        }

        /**
         *
         * sets @param timestamp
         */
        public void setTimestamp(final int timestamp) {
            this.timestamp = timestamp;
        }

        /**
         *
         * @return transfer type
         */
        public String getTransferType() {
            return transferType;
        }

        /**
         *
         * @return commerciant
         */
        public String getCommerciant() {
            return commerciant;
        }

        /**
         *
         * @return account
         */
        public String getAccount() {
            return account;
        }

        /**
         *
         * @return card
         */
        public String getCard() {
            return card;
        }

        /**
         *
         * @return card holder
         */
        public String getCardHolder() {
            return cardHolder;
        }

        /**
         *
         * @return amount (double)
         */
        public double getAmountOnline() {
            return amountOnline;
        }

        /**
         *
         * @return currency
         */
        public String getCurrency() {
            return currency;
        }

        /**
         *
         * @return involved accounts
         */
        public List<String> getInvolvedAccounts() {
            return involvedAccounts;
        }

        /**
         *
         * @return error
         */
        public String getError() {
            return error;
        }
    }

    /**
     *
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * sets @param amount
     */
    public void setAmount(final String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * sets @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return receiver's iban
     */
    public String getReceiverIban() {
        return receiverIban;
    }

    /**
     *
     * sets @param receiverIban
     */
    public void setReceiverIban(final String receiverIban) {
        this.receiverIban = receiverIban;
    }

    /**
     *
     * @return sender's iban
     */
    public String getSenderIban() {
        return senderIban;
    }

    /**
     *
     * sets @param senderIban
     */
    public void setSenderIban(final String senderIban) {
        this.senderIban = senderIban;
    }

    /**
     *
     * @return timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * sets@param timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return transfer type
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     *
     * sets @param transferType
     */
    public void setTransferType(final String transferType) {
        this.transferType = transferType;
    }

    /**
     *
     * @return commerciant
     */
    public String getCommerciant() {
        return commerciant;
    }

    /**
     *
     * sets @param commerciant
     */
    public void setCommerciant(final String commerciant) {
        this.commerciant = commerciant;
    }

    /**
     *
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     *
     * sets @param account
     */
    public void setAccount(final String account) {
        this.account = account;
    }

    /**
     *
     * @return card
     */
    public String getCard() {
        return card;
    }

    /**
     *
     * sets @param card
     */
    public void setCard(final String card) {
        this.card = card;
    }

    /**
     *
     * @return card holder
     */
    public String getCardHolder() {
        return cardHolder;
    }

    /**
     *
     * sets @param cardHolder
     */
    public void setCardHolder(final String cardHolder) {
        this.cardHolder = cardHolder;
    }

    /**
     *
     * @return amount(double)
     */
    public double getAmountOnline() {
        return amountOnline;
    }

    /**
     *
     * sets @param amount_online
     */
    public void setAmountOnline(final double amountOnline) {
        this.amountOnline = amountOnline;
    }

    /**
     *
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * sets @param currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return involved accounts
     */
    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    /**
     *
     * sets @param involvedAccounts
     */
    public void setInvolvedAccounts(final List<String> involvedAccounts) {
        this.involvedAccounts = involvedAccounts;
    }

    /**
     *
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     *
     * sets @param error
     */
    public void setError(final String error) {
        this.error = error;
    }
}
