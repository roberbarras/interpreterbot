package org.telegram.bot.interpreterbot.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "message_received")
public class MessageReceivedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Integer clientId;
    @Column
    private String name;
    @Column
    private String language;
    @Column
    private long chatId;
    @Column
    private String text;
    @Column
    private LocalDateTime creationDate;
}
