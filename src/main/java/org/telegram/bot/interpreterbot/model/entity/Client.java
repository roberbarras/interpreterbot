package org.telegram.bot.interpreterbot.model.entity;

import lombok.*;
import org.telegram.bot.interpreterbot.model.internal.UserLanguage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
public class Client implements Serializable {

    @Id
    @Column
    private Long clientId;

    @Column
    private LocalDateTime creationDate;

    @Column(name = "language")
    private UserLanguage userLanguage;

    @Column
    private String name;

    @Column
    private boolean auth;

    public void setUserLanguage(UserLanguage userLanguage) {
        this.userLanguage = userLanguage;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
