package org.poo.main;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRate {
    private String from;
    private String to;
    private double rate;

    public ExchangeRate(String from, String to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static ExchangeRate calculateExchangeRate(Account account, String currency, List<ExchangeRate> exchangeRates) {
        ExchangeRate exchangeRate = new ExchangeRate(account.getCurrency(), currency, 1);
        if (account.getCurrency().equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(currency) && exchangeRate1.getTo().equals(account.getCurrency())) {
                String aux = exchangeRate.getFrom();
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(aux);
                exchangeRate.setRate(1 / exchangeRate1.getRate());
                return exchangeRate;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) && exchangeRate1.getTo().equals(currency)) {
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
            if (exchangeRate1.getFrom().equals(exchangeRate2.getFrom()) && exchangeRate1.getTo().equals(currency)) {
                exchangeRate2.setFrom(exchangeRate1.getFrom());
                exchangeRate2.setTo(exchangeRate1.getTo());
                exchangeRate2.setRate(exchangeRate2.getRate() * exchangeRate1.getRate());
                return exchangeRate2;
            }
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getFrom().equals(account.getCurrency()) || exchangeRate1.getTo().equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getFrom());
                exchangeRate.setTo(exchangeRate1.getTo());
                exchangeRate.setRate(exchangeRate.getRate() * exchangeRate1.getRate());
            }
        }
        if (exchangeRate.getFrom().equals(account.getCurrency()) && exchangeRate.getTo().equals(currency)) {
            return exchangeRate;
        }
        for (ExchangeRate exchangeRate1 : exchangeRates) {
            if (exchangeRate1.getTo().equals(account.getCurrency()) || exchangeRate1.getFrom().equals(currency)) {
                exchangeRate.setFrom(exchangeRate1.getTo());
                exchangeRate.setTo(exchangeRate1.getFrom());
                exchangeRate.setRate(exchangeRate.getRate() * 1 / exchangeRate1.getRate());
            }
        }
        return exchangeRate;
    }
}
