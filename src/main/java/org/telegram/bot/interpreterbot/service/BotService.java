package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.kafka.MessageReceived;

public interface BotService {

    void onUpdateReceived(MessageReceived messageReceived);
}
