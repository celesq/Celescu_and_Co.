package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Utils {

    private static final double ROUND = 100.00;
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
