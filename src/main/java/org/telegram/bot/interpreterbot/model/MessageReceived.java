package org.telegram.bot.interpreterbot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageReceived {

    private Integer clientId;
    private String name;
    private String language;
    private long chatId;
    private String text;
}
