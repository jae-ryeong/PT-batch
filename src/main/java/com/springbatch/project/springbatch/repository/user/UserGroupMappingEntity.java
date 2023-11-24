package com.springbatch.project.springbatch.repository.user;

import com.springbatch.project.springbatch.repository.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Entity
@Table(name = "user_group_mapping")
@IdClass(UserGroupMappingId.class)  // 복합키 이기때문에 Id클래스를 선언
public class UserGroupMappingEntity extends BaseEntity {

    @Id
    private String userGroupId;

    @Id
    private String userId;

    private String userGroupName;
    private String description;
}
