package org.poo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.ObjectInput;
import org.poo.bank.ExchangeRate;
import org.poo.bank.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.poo.utils.Utils.resetRandom;

public final class Start {

    /**
     * parses input data and starts the iteration through the commands
     * @param filePath1 input
     * @param filePath2 output
     * @throws IOException if exception
     */
    public static void parseDataAndStart(final String filePath1, final String filePath2)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(CheckerConstants.TESTS_PATH + filePath1);
        ObjectInput inputData = objectMapper.readValue(file, ObjectInput.class);

        ArrayNode output = objectMapper.createArrayNode();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < inputData.getUsers().length; i++) {
            User newUser = new User(inputData.getUsers()[i].getFirstName(), inputData.getUsers()[i]
                    .getLastName(),
                    inputData.getUsers()[i].getEmail());
            users.add(newUser);
        }

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (int i = 0; i < inputData.getExchangeRates().length; i++) {
            ExchangeRate exchangeRate = new ExchangeRate(inputData.getExchangeRates()[i].getFrom(),
                    inputData.getExchangeRates()[i].getTo(), inputData.getExchangeRates()[i]
                    .getRate());
            exchangeRates.add(exchangeRate);
        }

        List<Comerciant> comerciants = new ArrayList<>();
        if (inputData.getCommerciants() != null) {
            for (int i = 0; i < inputData.getCommerciants().length; i++) {
                Comerciant comerciant = new Comerciant(inputData.getCommerciants()[i].getId(),
                        inputData.getCommerciants()[i].getDescription(),
                        inputData.getCommerciants()[i].getCommerciants());
                comerciants.add(comerciant);
            }
        }

        resetRandom();
        Output output1 = new Output(users, exchangeRates, comerciants, inputData.getCommands(),
                output);
        output1.iterateCommands();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
