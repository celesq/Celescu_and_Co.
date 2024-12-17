package org.poo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts = new ArrayList<>();
    private Map<String, String> aliases = new HashMap<>();

    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *
     * @return accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     *
     * sets @param accounts
     */
    public void setAccounts(final ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * sets @param email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * sets @param firstName
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * sets @param lastName
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return aliases
     */
    public Map<String, String> getAliases() {
        return aliases;
    }

    /**
     *
     * sets @param aliases
     */
    public void setAliases(final Map<String, String> aliases) {
        this.aliases = aliases;
    }
}
