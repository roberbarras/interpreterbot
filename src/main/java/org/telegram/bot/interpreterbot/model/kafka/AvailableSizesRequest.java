package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AvailableSizesRequest implements Serializable {

    private String clientId;
    private String chatId;
    private UserLanguage language;
    private String url;
}
