package org.telegram.bot.interpreterbot.telegram.commands;

import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.model.MessageToSend;

public interface Command {

    MessageToSend process(MessageReceived messageReceived, Client client);

}
