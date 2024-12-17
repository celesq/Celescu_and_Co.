package org.poo.main;

public class AccountFactory {

    /**
     *
     * @param accountType the type of account which needs to be created
     * @param iban account IBAN
     * @param currency account currency
     * @param interestRate account's interest rate
     * @return account created
     */
    static Account createAccount(final String accountType, final String iban, final String currency,
                                 final double interestRate) {
        return switch (accountType) {
            case "savings" -> new SavingsAccount(iban, currency, accountType, interestRate);
            case "classic" -> new ClassicAccount(iban, currency, accountType);
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
}
