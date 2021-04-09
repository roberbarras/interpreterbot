package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.internal.BotStatus;
import org.telegram.bot.interpreterbot.model.internal.TemporaryInfo;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.languages.Language;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RequestDeleteCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        List<Garment> garments = getGarmentService().findByClientId(client.getClientId());
        Language language = getLanguage(client.getUserLanguage());
        AtomicInteger i = new AtomicInteger(1);

        String table = Optional.of(garments)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(elem -> "/" + i.getAndIncrement() + " " + elem.getName() + " " + language.getSize() + " " + elem.getSize())
                        .collect(Collectors.joining("\n", language.getRequestDeleteMessage() + "\n", "")))
                .orElse(language.getEmptyGarmentListMessage());

        Optional.of(table)
                .filter(message -> message.equals(language.getEmptyGarmentListMessage()))
                .ifPresentOrElse((m) -> {
                            getTemporaryInfoService().delete(client.getClientId());
                        },
                        () -> {
                            getTemporaryInfoService().save(client.getClientId(), TemporaryInfo.builder()
                                    .botStatus(BotStatus.CHOOSING_TO_DELETE)
                                    .erasableGarments(garments).build());
                        });

        return prepareMessage(table, messageReceived.getChatId(), false);
    }
}
