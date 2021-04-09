package org.telegram.bot.interpreterbot.model.kafka;

import lombok.Builder;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
public class AvailableSizesResponse {

    private String url;

    private Map<String, Boolean> sizes;
}
