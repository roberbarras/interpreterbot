package org.telegram.bot.interpreterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.bot.interpreterbot.model.entity.Garment;

import java.util.List;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, Long> {

    List<Garment> findByClientIdAndEnabledTrueOrderByCreationDateAsc(Long clientId);

    List<Garment> findByClientIdAndEnabledFalseOrderByCreationDateAsc(Long clientId);
}
