package org.telegram.bot.interpreterbot.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageToSend implements Serializable {

    private String chatId;
    private String text;
    private String parseMode;
    private boolean disableWebPagePreview;
}
