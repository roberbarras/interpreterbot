package org.telegram.bot.interpreterbot.telegram.commands;

import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;
import org.telegram.bot.interpreterbot.model.kafka.MessageToSend;

public interface Command {

    MessageToSend process(MessageReceived messageReceived, Client client);

}
