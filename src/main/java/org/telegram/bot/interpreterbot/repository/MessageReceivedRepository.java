package org.telegram.bot.interpreterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telegram.bot.interpreterbot.model.entity.MessageReceivedEntity;

public interface MessageReceivedRepository extends JpaRepository<MessageReceivedEntity, Long> {
}
