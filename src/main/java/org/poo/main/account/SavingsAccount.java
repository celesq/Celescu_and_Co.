package org.poo.main.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SavingsAccount extends ClassicAccount implements Account {

    private double interestRate;

    public SavingsAccount(final String iban, final String currency, final String accountType,
                          final double interestRate) {
        super(iban, currency, accountType);
        this.interestRate = interestRate;
    }

    /**
     * @return the interest rate of the account
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     *
     * @param interestRate interest rate
     */

    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     *
     * @param changedInterestRate the interest rate
     * @param timestamp timestamp
     * @return
     */
    public boolean changeInterestRate(final double changedInterestRate, final int timestamp) {
        this.interestRate = changedInterestRate;
        Transactions newTransaction = new Transactions.TransactionsBuilder(timestamp,
                "Interest rate of the account changed to " + interestRate).build();
        getTransactions().add(newTransaction);
        return true;
    }

    /**
     *
     * @return true because this is a savings account, the classic one returns false
     */
    public boolean addInterest() {
        balance = balance + balance * interestRate;
        return true;
    }

    /**
     *
     * @param startTimeStamp start point
     * @param endTimeStamp end point
     * @param timestamp this timestamp
     * @return
     */
    public ObjectNode makeSpendingsReport(final int startTimeStamp, final int endTimeStamp,
                                          final int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("error", "This kind of report is not supported for a saving " + "account");
        return objectNode;
    }
}
