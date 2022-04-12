package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.entity.Client;

public interface ClientService {

    void save(Client client);

    Client findById(Long id);
}
