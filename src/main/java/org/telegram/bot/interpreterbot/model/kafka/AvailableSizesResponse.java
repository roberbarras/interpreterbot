package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AvailableSizesResponse {

    private String clientId;
    private String chatId;
    private UserLanguage language;
    private String url;
    private Map<String, Boolean> sizes;
}
