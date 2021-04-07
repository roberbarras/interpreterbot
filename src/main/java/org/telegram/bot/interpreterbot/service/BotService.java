package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.MessageReceived;

public interface BotService {

    void onUpdateReceived(MessageReceived messageReceived);
}
