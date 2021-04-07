package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.model.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;

@Component
public class StartCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        return prepareMessage(
                getLanguage(client.getUserLanguage()).getBotDescription(client.getName()),
                messageReceived.getChatId(),
                false);
    }
}
