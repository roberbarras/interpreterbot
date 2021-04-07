package org.telegram.bot.interpreterbot.telegram.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.model.MessageToSend;
import org.telegram.bot.interpreterbot.telegram.commands.Command;
import org.telegram.bot.interpreterbot.telegram.commands.CommandSupplier;
import org.telegram.bot.interpreterbot.util.Constants;

import java.util.Optional;

@Component
public class OtherCommand extends BaseCommand implements Command {

    @Autowired
    CommandSupplier commandSupplier;

    @Autowired
    ProcessUrlCommand processUrlCommand;

    @Override
    public MessageToSend process(MessageReceived messageReceived, Client client) {
        return Optional.ofNullable(commandSupplier
                .supplyCommand(Optional.ofNullable(getTemporaryInfoService()
                        .findById(messageReceived.getClientId()))
                        .map(elem -> elem.getBotStatus().name())
                        .orElse(Constants.OTHER_COMMAND)))
                .orElse(processUrlCommand)
                .process(messageReceived, client);
    }
}