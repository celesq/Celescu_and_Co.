package org.poo.account;

public interface BalanceObserver {

    /**
     *
     * @param checkedAccount account to be checked
     * @param timestamp current timestamp
     */
    void update(Account checkedAccount, int timestamp);
}
