package com.springbatch.project.springbatch.repository.pass;

import com.springbatch.project.springbatch.repository.BaseEntity;
import com.springbatch.project.springbatch.repository.packaze.PackageEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "pass")
public class PassEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passSeq;

    private Integer packageSeq;

    private String userId;

    private PassStatus status;

    private Integer remainingCount; // 남은 횟수

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageSeq", insertable = false, updatable = false)
    private PackageEntity packageEntity;
}
