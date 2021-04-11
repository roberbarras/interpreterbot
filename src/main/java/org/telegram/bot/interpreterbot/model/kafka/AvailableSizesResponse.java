package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AvailableSizesResponse {

    private String url;

    private Map<String, Boolean> sizes;
}
