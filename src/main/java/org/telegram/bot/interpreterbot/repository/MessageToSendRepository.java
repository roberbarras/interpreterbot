package org.telegram.bot.interpreterbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telegram.bot.interpreterbot.model.entity.MessageToSendEntity;

public interface MessageToSendRepository extends JpaRepository<MessageToSendEntity, Long> {
}
