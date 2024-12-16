package org.poo.main;

import java.util.ArrayList;

public final class Utils {

    public static ExchangeRate calculateExchangeRate(Account account, String currency, ArrayList<ExchangeRate> exchangeRates) {
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

    public static double roundToTwoDecimalPlates(double value) {
        return value * 100.00 / 100.00;
    }
}
