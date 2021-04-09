package org.telegram.bot.interpreterbot.model.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.telegram.bot.interpreterbot.model.entity.Garment;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class TemporaryInfo implements Serializable {

    private BotStatus botStatus;

    @JsonInclude(Include.NON_NULL)
    private Garment newGarment;

    @JsonInclude(Include.NON_NULL)
    private List<Garment> erasableGarments;

//    @JsonInclude(Include.NON_NULL)
//    private List<String> selectableSizes;

}
