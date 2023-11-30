package com.springbatch.project.springbatch.controller.admin;

import com.springbatch.project.springbatch.util.LocalDateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class BulkPassRequest {
    private Integer packageSeq;
    private String userGroupId;
    private LocalDateTime startedAt;

    public void setStartedAt(String startedAtString) {
        this.startedAt = LocalDateTimeUtils.parse(startedAtString);
    }
}
