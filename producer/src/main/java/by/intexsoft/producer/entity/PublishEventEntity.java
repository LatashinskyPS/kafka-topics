package by.intexsoft.producer.entity;

import by.intexsoft.producer.enums.State;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "publish_event")
@Data
public class PublishEventEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "message_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private MessageEntity message;

    @OneToOne
    @JoinColumn(name = "telegram_result_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private TelegramResultEntity result;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
}
