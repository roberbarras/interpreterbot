package org.telegram.bot.interpreterbot.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Garment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Long clientId;

    @Column
    private String url;

    @Column
    private String name;

    @Column
    private String size;

    @Column
    private boolean enabled;

    @Column
    private BigDecimal price;

    @Column
    private boolean notified;

    @Column
    private LocalDateTime creationDate;

    public void setSize(String size) {
        this.size = size;
    }
}
