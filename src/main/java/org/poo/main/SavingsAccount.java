package org.poo.main;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String Iban, double balance, String currency, String accountType, double interestRate) {
        super(Iban, balance, currency, accountType);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
