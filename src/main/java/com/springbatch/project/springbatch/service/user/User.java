package com.springbatch.project.springbatch.service.user;

import com.springbatch.project.springbatch.repository.user.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class User {

    private String userId;
    private String userName;
    private UserStatus status;
}
