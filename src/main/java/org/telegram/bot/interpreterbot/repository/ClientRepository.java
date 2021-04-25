package org.telegram.bot.interpreterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.bot.interpreterbot.model.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByClientIdAndAuthTrue(int clientId);

}
