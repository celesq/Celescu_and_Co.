package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SavingsAccount extends ClassicAccount implements Account {

    private double interestRate;

    public SavingsAccount(String Iban, String currency, String accountType, double interestRate) {
        super(Iban, currency, accountType);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public boolean changeInterestRate(double interestRate, int timestamp) {
        this.interestRate = interestRate;
        Transactions newTransaction = new Transactions.TransactionsBuilder(timestamp,
                "Interest rate of the account changed to " + interestRate).build();
        getTransactions().add(newTransaction);
        return true;
    }

    public boolean addInterest() {
        balance = balance + balance * interestRate;
        return true;
    }

    public ObjectNode makeSpendingsReport(int startTimeStamp, int endTimeStamp, int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("error", "This kind of report is not supported for a saving account");
        return objectNode;
    }
}
