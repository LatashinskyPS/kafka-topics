package by.intexsoft.producer.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "telegram_result")
@Data
public class TelegramResultEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @Column(name = "message_ID")
    private Long messageId;

    @Column(name = "date")
    private Long date;

    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "from_uuid")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private TelegramUserEntity from;

    @OneToOne
    @JoinColumn(name = "chat_uuid")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private TelegramUserEntity chat;
}
