package com.springbatch.project.springbatch.repository.notification;

import com.springbatch.project.springbatch.repository.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationSeq;

    private String uuid;

    private NotificationEvent event;

    private String text;

    private boolean sent;

    private LocalDateTime sentAt;
}
