package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.*;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.telegram.commands.Command;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RequestChangeLanguageCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        AtomicInteger i = new AtomicInteger(1);

        getTemporaryInfoService().save(client.getClientId(), TemporaryInfo
                .builder()
                .botStatus(BotStatus.CHOOSING_LANGUAGE)
                .build());

        return prepareMessage(
                getLanguage(client.getUserLanguage())
                        .getLanguageMessage() + "\n" +
                        Arrays.stream(UserLanguage.values())
                                .map(elem -> "/" + i.getAndIncrement() + " " + elem.getDescription())
                                .collect(Collectors.joining("\n")),
                messageReceived.getChatId(),
                false);
    }
}
