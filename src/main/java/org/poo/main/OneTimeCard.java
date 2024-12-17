package org.poo.main;

import java.util.List;

import static org.poo.main.ExchangeRate.calculateExchangeRate;
import static org.poo.main.Utils.roundToTwoDecimalPlates;
import static org.poo.utils.Utils.generateCardNumber;

public class OneTimeCard extends ClassicCard implements Card {

    public OneTimeCard(final String number, final String status) {
        super(number, status);
    }

    /**
     * @param account       account which wants to pay
     * @param card          card which is used to pay
     * @param amount        to pay
     * @param currency      currency needed to pay
     * @param description   payment description
     * @param commerciant   commerciant
     * @param email         user email
     * @param timestamp     timestamp
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
            newTransaction = new Transactions.TransactionsBuilder(timestamp,
                    "Insufficient funds").build();
            account.getTransactions().add(newTransaction);
            return;
        }
        newTransaction = new Transactions.TransactionsBuilder(timestamp,
                description).setAmountOnline(amount * 1 / exchangeRate.getRate())
                .setCommerciant(commerciant)
                .build();
        account.getTransactions().add(newTransaction);
        account.setBalance((account.getBalance() - amount / exchangeRate.getRate()));
        Transactions cardDestroyTransaction = new Transactions.TransactionsBuilder(timestamp,
                "The card has been destroyed").setCard(card.getNumber()).setCardHolder(email).
                setAccount(account.getIban()).build();
        account.getTransactions().add(cardDestroyTransaction);
        account.getCards().remove(card);
        Card newCard = new OneTimeCard(generateCardNumber(), getStatus());
        account.getCards().add(newCard);
        Transactions newCardCreated =
                new Transactions.TransactionsBuilder(timestamp, "New card created").setAccount(
                                account.getIban()).setCard(newCard.getNumber()).setCardHolder(email)
                .build();
        account.getTransactions().add(newCardCreated);
        card.update(account, timestamp);
    }

}
