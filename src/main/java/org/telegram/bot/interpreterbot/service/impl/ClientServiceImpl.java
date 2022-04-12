package org.telegram.bot.interpreterbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.entity.Client;
import org.telegram.bot.interpreterbot.repository.ClientCacheRepository;
import org.telegram.bot.interpreterbot.repository.ClientRepository;
import org.telegram.bot.interpreterbot.service.ClientService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientCacheRepository clientCacheRepository;

    @Override
    public void save(Client client) {
        CompletableFuture.runAsync(() -> clientRepository.save(client));
        CompletableFuture.runAsync(() -> clientCacheRepository.save(client.getClientId(), client));
    }

    @Override
    public Client findById(Long id) {
        LocalDateTime l1 = LocalDateTime.now();
        CompletableFuture<Client> cacheClient = CompletableFuture.supplyAsync(() -> clientCacheRepository.findById(id));
        CompletableFuture<Client> bbddClient = CompletableFuture.supplyAsync(() -> clientRepository.findById(id)
                .orElse(null));
        try {
            Client clientFromCache = cacheClient.join();
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer usuario de REDIS: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return clientFromCache;
        } catch (CompletionException e) {
            Client clientFromDDBB = bbddClient.join();
            CompletableFuture.runAsync(() -> clientCacheRepository.save(clientFromDDBB.getClientId(), clientFromDDBB));
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer usuario de BBDD: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return clientFromDDBB;
        }
    }

    public void clientPermission(Long clientId, boolean allow) {
        Optional<Client> client = clientRepository.findById(clientId);
        client.ifPresent(cli -> {
            cli.setAuth(allow);
            clientRepository.save(cli);
            clientCacheRepository.delete(cli.getClientId());
        });
    }
}
