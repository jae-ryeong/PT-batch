package com.springbatch.project.springbatch.repository.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.Map;

@Getter @Setter
@ToString
@Entity
@Table(name = "user")
//@Convert(converter = JsonType.class)
public class UserEntity {
    @Id
    private String userId;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String phone;

    private String uuid;

/*    // json 형태로 저장되어 있는 문자열 데이터를 Map으로 매핑
    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    private Map<String, Object> meta;*/

/*    public String getUuid() {
        String uuid = null;
        if (meta.containsKey("uuid")) {
            uuid = String.valueOf(meta.get("uuid"));
        }
        return uuid;
    }*/
}
