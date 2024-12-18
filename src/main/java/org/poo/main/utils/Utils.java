package org.poo.main.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.account.Transactions;

import java.util.Random;

public final class Utils {

    private static final double ROUND = 100.00;

    private static final int IBAN_SEED = 1;
    private static final int CARD_SEED = 2;
    private static final int DIGIT_BOUND = 10;
    private static final int DIGIT_GENERATION = 16;
    private static final String RO_STR = "RO";
    private static final String POO_STR = "POOB";


    private static Random ibanRandom = new Random(IBAN_SEED);
    private static Random cardRandom = new Random(CARD_SEED);

    private Utils() {
        // Checkstyle error free constructor
    }

    /**
     * Utility method for generating an IBAN code.
     *
     * @return the IBAN as String
     */
    public static String generateIBAN() {
        StringBuilder sb = new StringBuilder(RO_STR);
        for (int i = 0; i < RO_STR.length(); i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        sb.append(POO_STR);
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Utility method for generating a card number.
     *
     * @return the card number as String
     */
    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(cardRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Resets the seeds between runs.
     */
    public static void resetRandom() {
        ibanRandom = new Random(IBAN_SEED);
        cardRandom = new Random(CARD_SEED);
    }
    /**
     *
     * @param value value to be rounded
     * @return rounded value
     */
    public static double roundToTwoDecimalPlates(final double value) {
        return value * ROUND / ROUND;
    }

    /**
     *
     * @param objectNode object node which will be used
     * @param transactions transaction to be put in the object node
     */
    public static void putTransactionInObject(final ObjectNode objectNode,
                                              final Transactions transactions) {
        objectNode.put("timestamp", transactions.getTimestamp());
        objectNode.put("description", transactions.getDescription());
        if (transactions.getCard() != null) {
            objectNode.put("card", transactions.getCard());
        }
        if (transactions.getCardHolder() != null) {
            objectNode.put("cardHolder", transactions.getCardHolder());
        }
        if (transactions.getAccount() != null) {
            objectNode.put("account", transactions.getAccount());
        }
        if (transactions.getSenderIban() != null) {
            objectNode.put("senderIBAN", transactions.getSenderIban());
        }
        if (transactions.getReceiverIban() != null) {
            objectNode.put("receiverIBAN", transactions.getReceiverIban());
        }
        if (transactions.getCurrency() != null) {
            objectNode.put("currency", transactions.getCurrency());
        }
        if (transactions.getAmount() != null) {
            objectNode.put("amount", transactions.getAmount());
        }
        if (transactions.getAmountOnline() != 0) {
            objectNode.put("amount", transactions.getAmountOnline());
        }
        if (transactions.getInvolvedAccounts() != null) {
            ArrayNode arrayNode2 = new ObjectMapper().createArrayNode();
            for (String accounts : transactions.getInvolvedAccounts()) {
                arrayNode2.add(accounts);
            }
            objectNode.put("involvedAccounts", arrayNode2);
        }
        if (transactions.getError() != null) {
            objectNode.put("error", transactions.getError());
        }
        if (transactions.getTransferType() != null) {
            objectNode.put("transferType", transactions.getTransferType());
        }
        if (transactions.getCommerciant() != null) {
            objectNode.put("commerciant", transactions.getCommerciant());
        }
    }
}
