package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.languages.Language;

public class CleanCommand extends BaseCommand implements Command {
    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        Language language = getLanguage(client.getUserLanguage());

        getGarmentService().deleteDisabled(client.getClientId());

        return prepareMessage(language.getCleanMessage(), messageReceived.getChatId(), false);
    }
}
