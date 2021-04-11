package org.telegram.bot.interpreterbot.model.kafka;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AvailableSizesRequest implements Serializable {

    private String url;
}
