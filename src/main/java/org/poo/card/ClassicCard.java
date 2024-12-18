package org.poo.card;

import org.poo.bank.ExchangeRate;
import org.poo.account.Transactions;
import org.poo.account.Account;

import java.util.List;

import static org.poo.bank.ExchangeRate.calculateExchangeRate;
import static org.poo.utils.Utils.roundToTwoDecimalPlates;

public class ClassicCard implements Card {
    private static final int MAX_CARD_LIMIT = 30;
    private String number;
    private String status;

    public ClassicCard(final String number, final String status) {
        this.number = number;
        this.status = status;
    }

    /**
     * @return card number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number setter
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * @return card status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status setter
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     *
     * @param account account which wants to pay
     * @param card card which is used to pay
     * @param amount to pay
     * @param currency currency needed to pay
     * @param description payment description
     * @param commerciant commerciant
     * @param email user email
     * @param timestamp timestamp
     * @param exchangeRates bank's exchange rates
     */
    @Override
    public void pay(final Account account, final Card card, final double amount,
                    final String currency, final String description, final String commerciant,
                    final String email, final int timestamp,
                    final List<ExchangeRate> exchangeRates) {
        Transactions newTransaction;
        ExchangeRate exchangeRate = calculateExchangeRate(account, currency, exchangeRates);
        exchangeRate.setRate(roundToTwoDecimalPlates(exchangeRate.getRate()));
        if (account.getBalance() * exchangeRate.getRate() < amount
                || card.getStatus().equals("frozen")) {
            newTransaction =
                    new Transactions.TransactionsBuilder(timestamp, "Insufficient funds")
                            .build();
            account.getTransactions().add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp, description)
                .setAmountOnline(amount * 1 / exchangeRate.getRate()).setCommerciant(commerciant)
                .build();
        account.getTransactions().add(newTransaction);
        account.setBalance((account.getBalance() - amount / exchangeRate.getRate()));
        card.update(account, timestamp);
    }

    /**
     *
     * @param checkedAccount account observed
     * @param timestamp timestamp
     */
    @Override
    public void update(final Account checkedAccount, final int timestamp) {
        if (checkedAccount.getBalance() <= checkedAccount.getMinBalance()) {
            setStatus("frozen");
            Transactions transaction = new Transactions.TransactionsBuilder(timestamp,
                    "You have reached the minimum amount of funds, "
                            + "the card will be frozen").build();
            checkedAccount.getTransactions().add(transaction);
        } else if (checkedAccount.getBalance() - checkedAccount.getMinBalance() <= MAX_CARD_LIMIT) {
            setStatus("warning");
            Transactions transaction =
                    new Transactions.TransactionsBuilder(timestamp, "Card warning")
                            .build();
            checkedAccount.getTransactions().add(transaction);
        }
    }
}
