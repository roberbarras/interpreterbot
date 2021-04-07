package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.*;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.languages.Language;

@Component
public class ApplyDeleteCommand extends BaseCommand implements Command {

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {

        TemporaryInfo info = getTemporaryInfoService().findById(messageReceived.getClientId());
        String elementToDelete = messageReceived.getText().replace("/", "");
        Language language = getLanguage(client.getUserLanguage());
        String message;

        try {
            Garment garmentToDelete = info.getErasableGarments().get(Integer.parseInt(elementToDelete) - 1);
            getGarmentService().deleteById(garmentToDelete.getId());
            message = language.getConfirmDeleteMessage("'" + garmentToDelete.getName() + "'");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            message = language.getConfirmDeleteErrorMessage(elementToDelete);
        }

        getTemporaryInfoService().delete(messageReceived.getClientId());

        return prepareMessage(message, messageReceived.getChatId(), false);
    }
}
