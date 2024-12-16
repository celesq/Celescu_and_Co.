package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.poo.main.Utils.putTransactionInObject;
import static org.poo.utils.Utils.generateCardNumber;
import static org.poo.utils.Utils.generateIBAN;

public class Output {
    private List<User> users;
    private List<ExchangeRate> exchangeRates;
    private List<Comerciant> comerciants;
    private CommandInput[] commandInput;
    private ArrayNode output;

    public Output(List<User> users, List<ExchangeRate> exchangeRates, List<Comerciant> comerciants,
                  CommandInput[] commandInput, ArrayNode output) {
        this.users = users;
        this.exchangeRates = exchangeRates;
        this.comerciants = comerciants;
        this.commandInput = commandInput;
        this.output = output;
    }

    public List<Comerciant> getComerciants() {
        return comerciants;
    }

    public void setComerciants(List<Comerciant> comerciants) {
        this.comerciants = comerciants;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public CommandInput[] getCommandInput() {
        return commandInput;
    }

    public void setCommandInput(CommandInput[] commandInput) {
        this.commandInput = commandInput;
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
    }

    public void iterateCommands() {
        for (int i = 0; i < commandInput.length; i++) {
            switch (commandInput[i].getCommand()) {
                case "printUsers" -> printUsers(commandInput[i].getCommand(), commandInput[i].getTimestamp());
                case "addAccount" -> addAccount(commandInput[i].getEmail(), commandInput[i].getCurrency(),
                        commandInput[i].getAccountType(), commandInput[i].getTimestamp(),
                        commandInput[i].getInterestRate());
                case "createCard" -> createCard(commandInput[i].getAccount(), commandInput[i].getEmail(),
                        commandInput[i].getTimestamp());
                case "addFunds" -> addFunds(commandInput[i].getAccount(), commandInput[i].getAmount(),
                        commandInput[i].getTimestamp());
                case "deleteAccount" -> deleteAccount(commandInput[i].getCommand(), commandInput[i].getEmail(),
                        commandInput[i].getAccount(),
                        commandInput[i].getTimestamp());
                case "deleteCard" -> deleteCard(commandInput[i].getCardNumber(), commandInput[i].getTimestamp());
                case "createOneTimeCard" -> createOneTimeCard(commandInput[i].getAccount(), commandInput[i].getEmail(),
                        commandInput[i].getTimestamp());
                case "setMinBalance" -> setMinBalance(commandInput[i].getCommand(), commandInput[i].getMinBalance(),
                        commandInput[i].getAccount(), commandInput[i].getTimestamp());
                case "checkCardStatus" -> checkCardStatus(commandInput[i].getCommand(), commandInput[i].getCardNumber(),
                        commandInput[i].getTimestamp());
                case "payOnline" -> payOnline(commandInput[i].getCommand(), commandInput[i].getCardNumber(),
                        commandInput[i].getAmount(),
                        commandInput[i].getCurrency(), commandInput[i].getTimestamp(),
                        commandInput[i].getCommerciant()
                        , commandInput[i].getEmail());
                case "sendMoney" -> sendMoney(commandInput[i].getCommand(), commandInput[i].getAccount(),
                        commandInput[i].getAmount(),
                        commandInput[i].getReceiver(), commandInput[i].getTimestamp(),
                        commandInput[i].getDescription(),
                        commandInput[i].getEmail());
                case "printTransactions" -> printTransactions(commandInput[i].getCommand(), commandInput[i].getEmail(),
                        commandInput[i].getTimestamp());
                case "setAlias" ->
                        setAlias(commandInput[i].getEmail(), commandInput[i].getAlias(), commandInput[i].getAccount());
                case "splitPayment" -> splitPayment(commandInput[i].getAccounts(), commandInput[i].getCurrency(),
                        commandInput[i].getTimestamp(), commandInput[i].getAmount());
                case "changeInterestRate" ->
                        changeInterestRate(commandInput[i].getAccount(), commandInput[i].getInterestRate(),
                                commandInput[i].getTimestamp());
                case "addInterest" -> addInterest(commandInput[i].getAccount(), commandInput[i].getTimestamp());
                case "report" -> report(commandInput[i].getStartTimestamp(), commandInput[i].getEndTimestamp(),
                        commandInput[i].getAccount(), commandInput[i].getTimestamp());
                case "spendingsReport" ->
                        spendingsReport(commandInput[i].getStartTimestamp(), commandInput[i].getEndTimestamp(),
                                commandInput[i].getAccount(), commandInput[i].getTimestamp());
            }
        }
    }

    public void printUsers(String command, int timestamp) {
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

    public void addAccount(String email, String currency, String accountType, int timestamp, double interestRate) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                Account account;
                String Iban = generateIBAN();
                account = AccountFactory.createAccount(accountType, Iban, currency, interestRate);
                Transactions transaction = new Transactions.
                        TransactionsBuilder(timestamp, "New account created").build();
                account.getTransactions().add(transaction);
                user.getAccounts().add(account);
            }
        }
    }

    public void deleteAccount(String command, String email, String Iban, int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        Account removedAccount = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(Iban)) {
                        ObjectNode objectNode1 = mapper.createObjectNode();
                        if (account.getBalance() == 0) {
                            account.getCards().removeAll(account.getCards());
                            account.getTransactions().removeAll(account.getTransactions());
                            removedAccount = account;
                            objectNode1.put("success", "Account deleted");
                        } else {
                            objectNode1.put("error", "Account couldn't be deleted - " +
                                    "see org.poo.transactions for details");
                            Transactions newTransaction = new Transactions.TransactionsBuilder(timestamp,
                                    "Account couldn't be deleted - there are funds remaining").build();
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

    public void changeInterestRate(String Iban, double interestRate, int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
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

    public void addInterest(String Iban, int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
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

    public void createCard(String Iban, String email, int timestamp) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(Iban)) {
                        Card card = new ClassicCard(generateCardNumber(), "active");
                        account.getCards().add(card);
                        putTransactionForCard("New card created", account, card.getNumber(), user.getEmail(),
                                timestamp);
                    }
                }
            }
        }
    }

    public void createOneTimeCard(String Iban, String email, int timestamp) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIban().equals(Iban)) {
                        Card card = new OneTimeCard(generateCardNumber(), "active");
                        account.getCards().add(card);
                        putTransactionForCard("New card created", account, card.getNumber(), user.getEmail(),
                                timestamp);
                    }
                }
            }
        }
    }

    public void putTransactionForCard(String description, Account account, String card, String cardHolder,
                                      int timestamp) {
        Transactions transaction = new Transactions.TransactionsBuilder(timestamp, description)
                .setAccount(account.getIban()).setCard(card).setCardHolder(cardHolder).build();
        account.getTransactions().add(transaction);
    }

    public void deleteCard(String cardNumber, int timestamp) {
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
                    putTransactionForCard("The card has been destroyed", account, removedCard.getNumber(),
                            user.getEmail(), timestamp);
                }
            }
        }
    }

    public void addFunds(String Iban, double amount, int timestamp) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
                    account.setBalance(account.getBalance() + amount);
                }
            }
        }
    }

    public void setMinBalance(String command, double minBalance, String Iban, int timestamp) {
        int ok = 0;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
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

    public void checkCardStatus(String command, String cardNumber, int timestamp) {
        Card checkedCard = null;
        Account checkedAccount = null;
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getNumber().equals(cardNumber)) {
                        checkedCard = card;
                        checkedAccount = account;
                    }
                }
            }
        }
        try {
            checkedCard.update(checkedAccount, timestamp);
        } catch (Exception e) {
            ObjectNode objectnode1 = new ObjectMapper().createObjectNode();
            objectnode1.put("timestamp", timestamp);
            objectnode1.put("description", "Card not found");
            objectNode.put("output", objectnode1);
            objectNode.put("timestamp", timestamp);
            output.add(objectNode);
        }
    }

    public void payOnline(String command, String cardNumber, double amount, String currency, int timestamp,
                          String commerciant, String email) {
        int correctUser = 0;
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getNumber().equals(cardNumber)) {
                        correctUser = 1;
                        if (card.getStatus().equals("frozen")) {
                            Transactions transaction = new Transactions.TransactionsBuilder(timestamp
                                    , "The card is frozen").build();
                            account.getTransactions().add(transaction);
                            return;
                        }
                        card.pay(account, card, amount, currency, "Card payment", commerciant, email,
                                timestamp, exchangeRates);
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

    public void sendMoney(String command, String Iban, double amount, String reciever, int timestamp,
                          String description, String email) {
        Account sender = null, recieverAccount = null;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
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
                        reciever = user.getAliases().get(reciever);
                        for (User user1 : users) {
                            for (Account account : user1.getAccounts()) {
                                if (account.getIban().equals(reciever)) {
                                    recieverAccount = account;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sender != null && recieverAccount != null) {
            sender.sendMoney(command, amount, recieverAccount, timestamp, description, exchangeRates);
        }
    }

    public void printTransactions(String command, String email, int timestamp) {
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
                    public int compare(Transactions o1, Transactions o2) {
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

    public void setAlias(String email, String alias, String Iban) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.getAliases().putIfAbsent(alias, Iban);
            }
        }
    }

    public void splitPayment(List<String> accountsInput, String currency, int timestamp, double amount) {
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
            if (!account.checkEnoughForSplit(currency, amount, accountsForSplit.size(), exchangeRates)) {
                accountWhichCouldNotPay = account;
            }
        }
        if (accountWhichCouldNotPay != null) {
            errorSplittingPayment(accountsForSplit, accountWhichCouldNotPay, amount, currency, accountsForSplit.size(),
                    timestamp,
                    accountsInput);
            return;
        }
        for (Account account : accountsForSplit) {
            account.splitPayment(currency, amount, accountsInput.size(), exchangeRates, timestamp, accountsInput);
        }
    }

    public void errorSplittingPayment(List<Account> accountsForSplit, Account accountWhichCouldNotPay, double amount,
                                     String currency, int people, int timestamp, List <String> accountsInput) {
        for (Account account : accountsForSplit) {
            Transactions newTransaction;
            if (amount % 1 == 0) {
                newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of "
                        + amount + "0 " + currency).setAmount_online(amount / people).setCurrency(currency).
                        setInvolvedAccounts(accountsInput).setError("Account " + accountWhichCouldNotPay.getIban() +
                                " has insufficient funds for a split payment.").build();
            } else {
                newTransaction = new Transactions.TransactionsBuilder(timestamp, "Split payment of "
                        + amount + " " + currency).setAmount_online(amount / people).setCurrency(currency).
                        setInvolvedAccounts(accountsInput).setError("Account " + accountWhichCouldNotPay.getIban() +
                                " has insufficient funds for a split payment.").build();
            }
            account.getTransactions().add(newTransaction);
        }
    }

    public void report(int startTimeStamp, int endTimeStamp, String Iban, int timestamp) {
        int found = 0;
        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
        objectNode1.put("command", "report");
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
                    found = 1;
                    ObjectNode objectNode = account.makeReport(startTimeStamp, endTimeStamp, timestamp);
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

    public void spendingsReport(int startTimeStamp, int endTimeStamp, String Iban, int timestamp) {
        int found = 0;
        ObjectNode objectNode1 = new ObjectMapper().createObjectNode();
        objectNode1.put("command", "spendingsReport");
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(Iban)) {
                    found = 1;
                    ObjectNode objectNode = account.makeSpendingsReport(startTimeStamp, endTimeStamp, timestamp);
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