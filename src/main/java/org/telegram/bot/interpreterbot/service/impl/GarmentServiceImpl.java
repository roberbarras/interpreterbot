package org.telegram.bot.interpreterbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.bot.interpreterbot.model.entity.Garment;
import org.telegram.bot.interpreterbot.repository.GarmentCacheRepository;
import org.telegram.bot.interpreterbot.repository.GarmentRepository;
import org.telegram.bot.interpreterbot.service.GarmentService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
public class GarmentServiceImpl implements GarmentService {

    @Autowired
    GarmentRepository garmentRepository;

    @Autowired
    GarmentCacheRepository garmentCacheRepository;

    @Override
    public List<Garment> findByClientId(Long clientId) {
        LocalDateTime l1 = LocalDateTime.now();
        CompletableFuture<List<Garment>> cacheGarments = CompletableFuture.supplyAsync(() ->
                garmentCacheRepository.findById(clientId));
        CompletableFuture<List<Garment>> bbddGarments = CompletableFuture.supplyAsync(() ->
                garmentRepository.findByClientIdAndEnabledTrueOrderByCreationDateAsc(clientId));
        try {
            List<Garment> garmentsFromCache = cacheGarments.join();
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer prendas de REDIS: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return garmentsFromCache;
        } catch (CompletionException e) {
            List<Garment> garmentsFromDDBB = bbddGarments.join();
            CompletableFuture.runAsync(() -> garmentCacheRepository.save(clientId, garmentsFromDDBB));
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer prendas de BBDD: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return garmentsFromDDBB;
        }
    }

    @Override
    public List<Garment> findDisabledByClientId(Long clientId) {
        LocalDateTime l1 = LocalDateTime.now();
        CompletableFuture<List<Garment>> cacheGarments = CompletableFuture.supplyAsync(() ->
                garmentCacheRepository.findDisabledById(clientId));
        CompletableFuture<List<Garment>> bbddGarments = CompletableFuture.supplyAsync(() ->
                garmentRepository.findByClientIdAndEnabledFalseOrderByCreationDateAsc(clientId));
        try {
            List<Garment> garmentsFromCache = cacheGarments.join();
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer prendas de REDIS: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return garmentsFromCache;
        } catch (CompletionException e) {
            List<Garment> garmentsFromDDBB = bbddGarments.join();
            CompletableFuture.runAsync(() -> garmentCacheRepository.save(clientId, garmentsFromDDBB));
            LocalDateTime l2 = LocalDateTime.now();
            log.debug("Tiempo en leer prendas de BBDD: {} us", Duration.between(l1, l2).toNanos() / 1000);
            return garmentsFromDDBB;
        }
    }

    @Override
    public void deleteDisabled(Long clientId) {
        CompletableFuture.runAsync(() ->
                garmentRepository.findByClientIdAndEnabledFalseOrderByCreationDateAsc(clientId)
                        .forEach(garment -> deleteById(garment.getId())));
    }

    @Override
    public void deleteById(long garmentId) {
        CompletableFuture
                .runAsync(() -> {
                            Long clientId = garmentRepository.findById(garmentId).get().getClientId();
                            garmentRepository.deleteById(garmentId);
                            garmentCacheRepository.save(clientId, garmentRepository.findByClientIdAndEnabledTrueOrderByCreationDateAsc(clientId));
                            log.debug("Se termina de borrar la prenda {}", garmentId);
                        }
                );
    }

    @Override
    public void save(Garment garment) {
        CompletableFuture.runAsync(() -> {
            garmentRepository.save(garment);
            garmentCacheRepository.save(garment.getClientId(), garmentRepository.findByClientIdAndEnabledTrueOrderByCreationDateAsc(garment.getClientId()));
            log.debug("Se termina de guardar la prenda {}", garment.getName());
        });
    }
}
