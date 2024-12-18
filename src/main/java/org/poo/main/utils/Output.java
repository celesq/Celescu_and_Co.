package org.poo.main.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.main.account.Account;
import org.poo.main.account.AccountFactory;
import org.poo.main.card.Card;
import org.poo.main.card.ClassicCard;
import org.poo.main.bank.ExchangeRate;
import org.poo.main.card.OneTimeCard;
import org.poo.main.account.Transactions;
import org.poo.main.bank.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.poo.main.utils.Utils.generateCardNumber;
import static org.poo.main.utils.Utils.putTransactionInObject;
import static org.poo.main.utils.Utils.generateIBAN;

public class Output {
    private List<User> users;
    private List<ExchangeRate> exchangeRates;
    private List<Comerciant> comerciants;
    private CommandInput[] commandInput;
    private ArrayNode output;
    private static final int MAX_CARD_LIMIT = 30;

    public Output(final List<User> users, final List<ExchangeRate> exchangeRates,
                  final List<Comerciant> comerciants, final CommandInput[] commandInput,
                  final ArrayNode output) {
        this.users = users;
        this.exchangeRates = exchangeRates;
        this.comerciants = comerciants;
        this.commandInput = commandInput;
        this.output = output;
    }

    /**
     * @return commerciants
     */
    public List<Comerciant> getComerciants() {
        return comerciants;
    }

    /**
     * @param comerciants setter
     */
    public void setComerciants(final List<Comerciant> comerciants) {
        this.comerciants = comerciants;
    }

    /**
     * @return bank's exchange rates
     */
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    /**
     * @param exchangeRates setter
     */
    public void setExchangeRates(final List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    /**
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users setter
     */
    public void setUsers(final List<User> users) {
        this.users = users;
    }

    /**
     * @return command input
     */
    public CommandInput[] getCommandInput() {
        return commandInput;
    }

    /**
     * @param commandInput setter
     */
    public void setCommandInput(final CommandInput[] commandInput) {
        this.commandInput = commandInput;
    }

    /**
     * @return output
     */
    public ArrayNode getOutput() {
        return output;
    }

    /**
     * @param output setter
     */
    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    /**
     * iterate each command and call the adequate function
     */
    public void iterateCommands() {
        for (int i = 0; i < commandInput.length; i++) {
            switch (commandInput[i].getCommand()) {
                case "printUsers" ->
                        printUsers(commandInput[i].getCommand(), commandInput[i].getTimestamp());
                case "addAccount" ->
                        addAccount(commandInput[i].getEmail(), commandInput[i].getCurrency(),
                                commandInput[i].getAccountType(), commandInput[i].getTimestamp(),
                                commandInput[i].getInterestRate());
                case "createCard" ->
                        createCard(commandInput[i].getAccount(), commandInput[i].getEmail(),
                                commandInput[i].getTimestamp());
                case "addFunds" ->
                        addFunds(commandInput[i].getAccount(), commandInput[i].getAmount(),
                                commandInput[i].getTimestamp());
                case "deleteAccount" ->
                        deleteAccount(commandInput[i].getCommand(), commandInput[i].getEmail(),
                                commandInput[i].getAccount(),
                        commandInput[i].getTimestamp());
                case "deleteCard" ->
                        deleteCard(commandInput[i].getCardNumber(), commandInput[i].getTimestamp());
                case "createOneTimeCard" ->
                        createOneTimeCard(commandInput[i].getAccount(), commandInput[i].getEmail(),
                        commandInput[i].getTimestamp());
                case "setMinBalance" ->
                        setMinBalance(commandInput[i].getCommand(), commandInput[i].getMinBalance(),
                        commandInput[i].getAccount());
                case "checkCardStatus" -> checkCardStatus(commandInput[i].getCommand(),
                        commandInput[i].getCardNumber(),
                        commandInput[i].getTimestamp());
                case "payOnline" ->
                        payOnline(commandInput[i].getCommand(), commandInput[i].getCardNumber(),
                        commandInput[i].getAmount(),
                        commandInput[i].getCurrency(), commandInput[i].getTimestamp(),
                        commandInput[i].getCommerciant(), commandInput[i].getEmail());
                case "sendMoney" ->
                        sendMoney(commandInput[i].getCommand(), commandInput[i].getAccount(),
                        commandInput[i].getAmount(),
                        commandInput[i].getReceiver(), commandInput[i].getTimestamp(),
                        commandInput[i].getDescription(),
                        commandInput[i].getEmail());
                case "printTransactions" ->
                        printTransactions(commandInput[i].getCommand(), commandInput[i].getEmail(),
                                commandInput[i].getTimestamp());
                case "setAlias" -> setAlias(commandInput[i].getEmail(), commandInput[i].getAlias(),
                        commandInput[i].getAccount());
                case "splitPayment" ->
                        splitPayment(commandInput[i].getAccounts(), commandInput[i].getCurrency(),
                        commandInput[i].getTimestamp(), commandInput[i].getAmount());
                case "changeInterestRate" -> changeInterestRate(commandInput[i].getAccount(),
                        commandInput[i].getInterestRate(),
                                commandInput[i].getTimestamp());
                case "addInterest" ->
                        addInterest(commandInput[i].getAccount(), commandInput[i].getTimestamp());
                case "report" -> report(commandInput[i].getStartTimestamp(),
                        commandInput[i].getEndTimestamp(),
                        commandInput[i].getAccount(), commandInput[i].getTimestamp());
                case "spendingsReport" -> spendingsReport(commandInput[i].getStartTimestamp(),
                        commandInput[i].getEndTimestamp(),
                                commandInput[i].getAccount(), commandInput[i].getTimestamp());
                default -> {
                    break;
                }
            }
        }
    }

    /**
     *
     * @param command input command
     * @param timestamp current timestamp
     */
    public void printUsers(final String command, final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (User user : users) {
            ObjectNode objectNode1 = mapper.createObjectNode();
            objectNode1.put("firstName", user.getFirstName());
            objectNode1.put("lastName", user.getLastName());
            objectNode1.put("email", user.getEmail());
            ArrayNode arrayNode1 = mapper.createArrayNode();
            for (Account account : user.getAccounts()) {
                ObjectNode objectNode2 = mapper.createObjectNode();
                objectNode2.put("IBAN", account.getIban());
                objectNode2.put("balance", account.getBalance());
                objectNode2.put("currency", account.getCurrency());
                objectNode2.put("type", account.getAccountType());
                ArrayNode arrayNode2 = mapper.createArrayNode();
                for (Card card : account.getCards()) {
                    ObjectNode objectNode3 = mapper.createObjectNode();
                    objectNode3.put("cardNumber", card.getNumber());
                    objectNode3.put("status", card.getStatus());
                    arrayNode2.add(objectNode3);
                }
                objectNode2.put("cards", arrayNode2);
                arrayNode1.add(objectNode2);
            }
            objectNode1.put("accounts", arrayNode1);
            arrayNode.add(objectNode1);
        }
        objectNode.put("output", arrayNode);
        objectNode.put("timestamp", timestamp);
        output.add(objectNode);
    }

    /**
     *
     * @param email user email
     * @param currency account currency
     * @param accountType type of account
     * @param timestamp current timestamp
     * @param interestRate account interest rate
     */
    public void addAccount(final String email, final String currency, final String accountType,
                           final int timestamp, final double interestRate) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                Account account;
                String iban = generateIBAN();
                account = AccountFactory.createAccount(accountType, iban, currency, interestRate);
                Transactions transaction = new Transactions.
                        TransactionsBuilder(timestamp, "New account created").build();
                account.getTransactions().add(transaction);
                user.getAccounts().add(account);
            }
        }
    }

    /**
     *
     * @param command current command
     * @param email user email
     * @param iban account IBAN
     * @param timestamp current timestamp
     */
    public void deleteAccount(final String command, final String email, final String iban,
                              final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        Account removedAccount = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(iban)) {
                        ObjectNode objectNode1 = mapper.createObjectNode();
                        if (account.getBalance() == 0) {
                            account.getCards().removeAll(account.getCards());
                            account.getTransactions().removeAll(account.getTransactions());
                            removedAccount = account;
                            objectNode1.put("success", "Account deleted");
                        } else {
                            objectNode1.put("error", "Account couldn't be deleted - "
                                    + "see org.poo.transactions for details");
                            Transactions newTransaction = new Transactions.TransactionsBuilder(
                                    timestamp, "Account couldn't be deleted - "
                                            + "there are funds remaining").build();
                            account.getTransactions().add(newTransaction);
                        }
                        objectNode1.put("timestamp", timestamp);
                        objectNode.put("output", objectNode1);
                    }
                }
            }
            if (removedAccount != null) {
                user.getAccounts().remove(removedAccount);
            }
        }
        objectNode.put("timestamp", timestamp);
        output.add(objectNode);
    }

    /**
     *
     * @param iban account IBAN
     * @param interestRate changed interest rate
     * @param timestamp current timestamp
     */
    public void changeInterestRate(final String iban, final double interestRate,
                                   final int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    boolean ok = account.changeInterestRate(interestRate, timestamp);
                    if (!ok) {
                        ObjectNode objectNode = new ObjectMapper().createObjectNode();
                        objectNode.put("command", "changeInterestRate");
                        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                        objectNode1.put("description", "This is not a savings account");
                        objectNode1.put("timestamp", timestamp);
                        objectNode.put("output", objectNode1);
                        objectNode.put("timestamp", timestamp);
                        output.add(objectNode);
                    }
                }
            }
        }
    }

    /**
     *
     * @param iban account IBAN
     * @param timestamp current timestamp
     */
    public void addInterest(final String iban, final int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    boolean ok = account.addInterest();
                    if (!ok) {
                        ObjectNode objectNode = new ObjectMapper().createObjectNode();
                        objectNode.put("command", "addInterest");
                        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
                        objectNode1.put("description", "This is not a savings account");
                        objectNode1.put("timestamp", timestamp);
                        objectNode.put("output", objectNode1);
                        objectNode.put("timestamp", timestamp);
                        output.add(objectNode);
                    }
                }
            }
        }
    }

    /**
     *
     * @param iban account IBAN
     * @param email user email
     * @param timestamp current timestamp
     */
    public void createCard(final String iban, final String email, final int timestamp) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(iban)) {
                        Card card = new ClassicCard(generateCardNumber(), "active");
                        account.getCards().add(card);
                        putTransactionForCard("New card created", account,
                                card.getNumber(), user.getEmail(), timestamp);
                    }
                }
            }
        }
    }

    /**
     *
     * @param iban account IBAN
     * @param email user email
     * @param timestamp current timestamp
     */
    public void createOneTimeCard(final String iban, final String email, final int timestamp) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(iban)) {
                        Card card = new OneTimeCard(generateCardNumber(), "active");
                        account.getCards().add(card);
                        putTransactionForCard("New card created", account,
                                card.getNumber(), user.getEmail(), timestamp);
                    }
                }
            }
        }
    }

    /**
     *
     * @param description transaction description
     * @param account account IBAN
     * @param card card used
     * @param cardHolder card owner
     * @param timestamp current timestamp
     */
    public void putTransactionForCard(final String description, final Account account,
                                      final String card, final String cardHolder,
                                      final int timestamp) {
        Transactions transaction = new Transactions.TransactionsBuilder(timestamp, description)
                .setAccount(account.getIban()).setCard(card).setCardHolder(cardHolder).build();
        account.getTransactions().add(transaction);
    }

    /**
     *
     * @param cardNumber number of the card deleted
     * @param timestamp current timestamp
     */
    public void deleteCard(final String cardNumber, final int timestamp) {
        Card removedCard = null;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getNumber().equals(cardNumber)) {
                        removedCard = card;
                    }
                }
                if (removedCard != null) {
                    account.getCards().remove(removedCard);
                    putTransactionForCard("The card has been destroyed",
                            account, removedCard.getNumber(), user.getEmail(), timestamp);
                }
            }
        }
    }

    /**
     *
     * @param iban account in which the founds will be added
     * @param amount amount of money
     * @param timestamp current timestamp
     */
    public void addFunds(final String iban, final double amount, final int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    account.setBalance(account.getBalance() + amount);
                }
            }
        }
    }

    /**
     *
     * @param command current command
     * @param minBalance minimum balance
     * @param iban account IBAN
     */
    public void setMinBalance(final String command, final double minBalance, final String iban) {
        int ok = 0;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    account.setMinBalance(minBalance);
                    ok = 1;
                }
            }
        }
        if (ok == 0) {
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            objectNode.put("command", command);
            objectNode.put("output", "error");
            output.add(objectNode);
        }
    }

    /**
     *
     * @param command current command
     * @param cardNumber checked card number
     * @param timestamp current timestamp
     */
    public void checkCardStatus(final String command, final String cardNumber,
                                final int timestamp) {
        Account checkedAccount = null;
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getNumber().equals(cardNumber)) {
                        checkedAccount = account;
                    }
                }
            }
        }
        try {
            for (Card card : checkedAccount.getCards()) {
                if (checkedAccount.getBalance() <= checkedAccount.getMinBalance()
                || checkedAccount.getBalance() - checkedAccount.getMinBalance() <= MAX_CARD_LIMIT) {
                    card.update(checkedAccount, timestamp);
                }
            }
        } catch (Exception e) {
            ObjectNode objectnode1 = new ObjectMapper().createObjectNode();
            objectnode1.put("timestamp", timestamp);
            objectnode1.put("description", "Card not found");
            objectNode.put("output", objectnode1);
            objectNode.put("timestamp", timestamp);
            output.add(objectNode);
        }
    }

    /**
     *
     * @param command current command
     * @param cardNumber card which is used to pay
     * @param amount amount paid
     * @param currency currency used
     * @param timestamp current timestamp
     * @param commerciant current commerciant
     * @param email user email
     */
    public void payOnline(final String command, final String cardNumber, final double amount,
                          final String currency, final int timestamp, final String commerciant,
                          final String email) {
        int correctUser = 0;
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getNumber().equals(cardNumber)) {
                        correctUser = 1;
                        if (card.getStatus().equals("frozen")) {
                            Transactions transaction = new Transactions.TransactionsBuilder(
                                    timestamp, "The card is frozen").build();
                            account.getTransactions().add(transaction);
                            return;
                        }
                        card.pay(account, card, amount, currency, "Card payment",
                                commerciant, email, timestamp, exchangeRates);
                    }
                }
            }
        }
        if (correctUser == 0) {
            ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
            objectNode1.put("timestamp", timestamp);
            objectNode1.put("description", "Card not found");
            objectNode.put("output", objectNode1);
            objectNode.put("timestamp", timestamp);
            output.add(objectNode);
        } else {
            objectNode.put("output", "Transaction complete");
        }
    }

    /**
     *
     * @param command current command
     * @param iban account IBAN
     * @param amount amount sent
     * @param reciever receiver IBAN
     * @param timestamp current timestamp
     * @param description description of the payment
     * @param email user email
     */
    public void sendMoney(final String command, final String iban, final double amount,
                          final String reciever, final int timestamp, final String description,
                          final String email) {
        Account sender = null, recieverAccount = null;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    sender = account;
                }
                if (account.getIban().equals(reciever)) {
                    recieverAccount = account;
                }
            }
        }
        if (recieverAccount == null) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    if (user.getAliases().containsKey(reciever)) {
                        String recieverAlias = user.getAliases().get(reciever);
                        for (User user1 : users) {
                            for (Account account : user1.getAccounts()) {
                                if (account.getIban().equals(recieverAlias)) {
                                    recieverAccount = account;
                                }
                            }
                        }
                    }
                }
            }
        }
        try {
            sender.sendMoney(command, amount, recieverAccount, timestamp, description,
                    exchangeRates);
        } catch (Exception e) {
            System.out.println("Null pointer exception on sendMoney method, please reconsider.");
        }
    }

    /**
     *
     * @param command current command
     * @param email user email
     * @param timestamp current timestamp
     */
    public void printTransactions(final String command, final String email, final int timestamp) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                ArrayNode arrayNode = new ObjectMapper().createArrayNode();
                List<Transactions> allTransactions = new ArrayList<>();
                for (Account account : user.getAccounts()) {
                    allTransactions.addAll(account.getTransactions());
                }
                allTransactions.sort(new Comparator<Transactions>() {
                    @Override
                    public int compare(final Transactions o1, final Transactions o2) {
                        return o1.getTimestamp() - o2.getTimestamp();
                    }
                });
                for (Transactions transaction : allTransactions) {
                    ObjectNode objectNode2 = new ObjectMapper().createObjectNode();
                    putTransactionInObject(objectNode2, transaction);
                    arrayNode.add(objectNode2);
                }
                objectNode.put("output", arrayNode);
            }
        }
        objectNode.put("timestamp", timestamp);
        output.add(objectNode);
    }

    /**
     *
     * @param email user email
     * @param alias set
     * @param iban account IBAN
     */
    public void setAlias(final String email, final String alias, final String iban) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.getAliases().putIfAbsent(alias, iban);
            }
        }
    }

    /**
     *
     * @param accountsInput accounts which split the payment
     * @param currency used
     * @param timestamp current timestamp
     * @param amount paid
     */
    public void splitPayment(final List<String> accountsInput, final String currency,
                             final int timestamp, final double amount) {
        List<Account> accountsForSplit = new ArrayList<>();
        for (String accountInput : accountsInput) {
            for (User user : users) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(accountInput)) {
                        accountsForSplit.add(account);
                    }
                }
            }
        }
        Account accountWhichCouldNotPay = null;
        for (Account account : accountsForSplit) {
            if (!account.checkEnoughForSplit(currency, amount, accountsForSplit.size(),
                    exchangeRates)) {
                accountWhichCouldNotPay = account;
            }
        }
        if (accountWhichCouldNotPay != null) {
            errorSplittingPayment(accountsForSplit, accountWhichCouldNotPay, amount, currency,
                    accountsForSplit.size(), timestamp, accountsInput);
            return;
        }
        for (Account account : accountsForSplit) {
            account.splitPayment(currency, amount, accountsInput.size(), exchangeRates, timestamp,
                    accountsInput);
        }
    }

    /**
     *
     * @param accountsForSplit which split
     * @param accountWhichCouldNotPay who could not pay
     * @param amount that needed to be paid
     * @param currency used
     * @param people who contributed
     * @param timestamp current timestamp
     * @param accountsInput accounts who contributed
     */
    public void errorSplittingPayment(final List<Account> accountsForSplit,
                                      final Account accountWhichCouldNotPay, final  double amount,
                                      final String currency, final int people, final int timestamp,
                                      final List<String> accountsInput) {
        for (Account account : accountsForSplit) {
            Transactions newTransaction;
            if (amount % 1 == 0) {
                newTransaction = new Transactions.TransactionsBuilder(timestamp,
                        "Split payment of " + amount + "0 " + currency)
                        .setAmountOnline(amount / people).setCurrency(currency).
                                setInvolvedAccounts(accountsInput)
                        .setError("Account " + accountWhichCouldNotPay.getIban()
                                + " has insufficient funds for a split payment.").build();
            } else {
                newTransaction = new Transactions.TransactionsBuilder(timestamp,
                        "Split payment of " + amount + " " + currency)
                        .setAmountOnline(amount / people).setCurrency(currency).
                                setInvolvedAccounts(accountsInput)
                        .setError("Account " + accountWhichCouldNotPay.getIban()
                                + " has insufficient funds for a split payment.").build();
            }
            account.getTransactions().add(newTransaction);
        }
    }

    /**
     *
     * @param startTimeStamp start
     * @param endTimeStamp end
     * @param iban account IBAN
     * @param timestamp current timestamp
     */
    public void report(final int startTimeStamp, final int endTimeStamp, final String iban,
                       final int timestamp) {
        int found = 0;
        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
        objectNode1.put("command", "report");
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    found = 1;
                    ObjectNode objectNode = account.makeReport(startTimeStamp, endTimeStamp,
                            timestamp);
                    objectNode1.put("output", objectNode);
                }
            }
        }
        if (found == 0) {
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            objectNode.put("description", "Account not found");
            objectNode.put("timestamp", timestamp);
            objectNode1.put("output", objectNode);
        }
        objectNode1.put("timestamp", timestamp);
        output.add(objectNode1);
    }

    /**
     *
     * @param startTimeStamp start
     * @param endTimeStamp end
     * @param iban account IBAN
     * @param timestamp current timestamp
     */
    public void spendingsReport(final int startTimeStamp, final int endTimeStamp, final String iban,
                                final int timestamp) {
        int found = 0;
        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
        objectNode1.put("command", "spendingsReport");
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    found = 1;
                    ObjectNode objectNode = account.makeSpendingsReport(startTimeStamp,
                            endTimeStamp, timestamp);
                    objectNode1.put("output", objectNode);
                }
            }
        }
        if (found == 0) {
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            objectNode.put("description", "Account not found");
            objectNode.put("timestamp", timestamp);
            objectNode1.put("output", objectNode);
        }
        objectNode1.put("timestamp", timestamp);
        output.add(objectNode1);
    }
}
