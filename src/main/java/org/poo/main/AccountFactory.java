package org.poo.main;

public class AccountFactory {

    public static Account createAccount(String accountType, String Iban, String currency, double interestRate) {
        return switch (accountType) {
            case "savings" -> new SavingsAccount(Iban, currency, accountType, interestRate);
            case "classic" -> new ClassicAccount(Iban, currency, accountType);
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
}
