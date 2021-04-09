package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;

import java.util.concurrent.CompletableFuture;

@Component
public class ProcessSizeCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        Garment garment = getTemporaryInfoService().findById(client.getClientId()).getNewGarment();

        garment.setSize(messageReceived.getText());

        CompletableFuture.runAsync(() -> getGarmentService().save(garment));

        getTemporaryInfoService().delete(messageReceived.getClientId());

        return prepareMessage(
                getLanguage(client.getUserLanguage())
                        .getConfirmSaveGarmentMessage(garment.getName(), garment.getSize()),
                messageReceived.getChatId(),
                false
        );
    }
}
