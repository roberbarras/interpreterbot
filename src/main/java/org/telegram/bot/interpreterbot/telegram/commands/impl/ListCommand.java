package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.languages.Language;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class ListCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        Language language = getLanguage(client.getUserLanguage());

        String table = Optional.of(getGarmentService().findByClientId(client.getClientId())
                .stream()
                .map(elem -> "<a href='" + elem.getUrl() + "'>" + elem.getName() + "</a> "
                        + language.getSize() + " " + elem.getSize())
                .collect(Collectors.joining("\n")))
                .filter(elem -> !elem.equals(""))
                .orElse(language.getEmptyGarmentListMessage());

        CompletableFuture.runAsync(() -> getTemporaryInfoService().delete(client.getClientId()));

        return prepareMessage(table, messageReceived.getChatId(), true, "html");
    }
}
