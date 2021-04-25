package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.internal.TemporaryInfo;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.service.ClientService;
import org.telegram.bot.interpreterbot.telegram.commands.Command;

import java.util.Arrays;

@Component
public class ApplyLanguageCommand extends BaseCommand implements Command {

    @Autowired
    private ClientService clientService;

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        TemporaryInfo info = getTemporaryInfoService().findById(messageReceived.getClientId());
        String languageToSet = messageReceived.getText().replace("/", "");
        String message;

        try {
            UserLanguage userLanguage = Arrays.asList(UserLanguage.values()).get(Integer.parseInt(languageToSet) - 1);
            client.setUserLanguage(userLanguage);
            clientService.save(client);
            message = getLanguage(client.getUserLanguage()).getConfirmChangeLanguage(userLanguage.getDescription());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            message = getLanguage(client.getUserLanguage()).getConfirmChangeLanguageErrorMessage();
        }

        getTemporaryInfoService().delete(messageReceived.getClientId());

        return prepareMessage(message, messageReceived.getChatId(), false);
    }
}
