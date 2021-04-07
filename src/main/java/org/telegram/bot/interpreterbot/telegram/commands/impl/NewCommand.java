package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.*;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.telegram.commands.Command;

import java.util.concurrent.CompletableFuture;

@Component
public class NewCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        CompletableFuture.runAsync(() -> getTemporaryInfoService().save(client.getClientId(), TemporaryInfo
                .builder()
                .botStatus(BotStatus.WAITING_URL)
                .build()));

        return prepareMessage(
                getLanguage(client.getUserLanguage()).getNewGarmentMessage(),
                messageReceived.getChatId(), false);
    }
}
