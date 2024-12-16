package org.poo.main;

import java.util.ArrayList;
import java.util.List;

public interface Card extends BalanceObserver {

    public String getNumber();

    public void setNumber(String number);

    public String getStatus();

    public void setStatus(String status);

    public void pay(Account account , Card card, double amount, String currency , String description, String commerciant,
                    String email, int timestamp, List<ExchangeRate> exchangeRates);
}
