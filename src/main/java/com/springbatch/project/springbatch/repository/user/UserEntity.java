package com.springbatch.project.springbatch.repository.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    private String userId;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String phone;
    private String meta;
}
