package org.poo.main.bank;

import org.poo.main.account.Account;

import java.util.List;

public class ExchangeRate {
    private String from;
    private String to;
    private double rate;

    public ExchangeRate(final String from, final String to, final double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    /**
     *
     * @return get from
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from setter
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     *
     * @return rate
     */
    public double getRate() {
        return rate;
    }

    /**
     *
     * @param rate setter
     */
    public void setRate(final double rate) {
        this.rate = rate;
    }

    /**
     *
     * @return to
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to setter
     */
    public void setTo(final String to) {
        this.to = to;
    }

    /**
     *
     * @param account account which wants to pay
     * @param currency currency of the payment
     * @param exchangeRates bank's exchange rates
     * @return modified exchange rate
     */
    public static ExchangeRate calculateExchangeRate(final Account account, final String currency,
                                                     final List<ExchangeRate> exchangeRates) {
        ExchangeRate exchangeRate = new ExchangeRate(account.getCurrency(), currency, 1);
        if (account.getCurrency().equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(currency) && exchangeRate1.getTo()
                    .equals(account.getCurrency())) {
                String aux = exchangeRate.getFrom();
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(aux);
                exchangeRate.setRate(1 / exchangeRate1.getRate());
                return exchangeRate;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) && exchangeRate1.getTo()
                    .equals(currency)) {
                return exchangeRate1;
            }
        }
        ExchangeRate exchangeRate2 = new ExchangeRate(exchangeRate.getFrom(), exchangeRate.getTo(),
                exchangeRate.getRate());
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getTo().equals(account.getCurrency())) {
                exchangeRate2.setFrom(exchangeRate1.getFrom());
                exchangeRate2.setTo(exchangeRate1.getTo());
                exchangeRate2.setRate(1 / exchangeRate1.getRate());
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(exchangeRate2.getFrom()) && exchangeRate1.getTo()
                    .equals(currency)) {
                exchangeRate2.setFrom(exchangeRate1.getFrom());
                exchangeRate2.setTo(exchangeRate1.getTo());
                exchangeRate2.setRate(exchangeRate2.getRate() * exchangeRate1.getRate());
                return exchangeRate2;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) || exchangeRate1.getTo()
                    .equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getFrom());
                exchangeRate.setTo(exchangeRate1.getTo());
                exchangeRate.setRate(exchangeRate.getRate() * exchangeRate1.getRate());
            }
        }
        if (exchangeRate.getFrom().equals(account.getCurrency()) && exchangeRate.getTo()
                .equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getTo().equals(account.getCurrency()) || exchangeRate1.getFrom()
                    .equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(exchangeRate1.getFrom());
                exchangeRate.setRate(exchangeRate.getRate() * 1 / exchangeRate1.getRate());
            }
        }
        return exchangeRate;
    }
}
