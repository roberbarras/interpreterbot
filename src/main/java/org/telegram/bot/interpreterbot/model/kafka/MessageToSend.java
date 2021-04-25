package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;

import java.io.Serializable;

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
