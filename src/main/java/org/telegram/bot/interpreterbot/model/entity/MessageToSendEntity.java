package org.telegram.bot.interpreterbot.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "message_to_send")
public class MessageToSendEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String chatId;
    @Column
    private String text;
    @Column
    private String parseMode;
    @Column
    private boolean disableWebPagePreview;
    @Column
    private LocalDateTime creationDate;
}
