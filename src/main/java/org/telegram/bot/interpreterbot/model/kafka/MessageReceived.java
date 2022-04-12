package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageReceived {

    private Long clientId;
    private String name;
    private String language;
    private long chatId;
    private String text;
}
