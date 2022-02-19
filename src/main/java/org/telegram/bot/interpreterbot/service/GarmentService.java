package org.telegram.bot.interpreterbot.service;

import org.telegram.bot.interpreterbot.model.entity.Garment;

import java.util.List;

public interface GarmentService {

    List<Garment> findByClientId(int clientId);

    List<Garment> findDisabledByClientId(int clientId);

    void deleteDisabled(int clientId);

    void deleteById(long garmentId);

    void save(Garment garment);
}
