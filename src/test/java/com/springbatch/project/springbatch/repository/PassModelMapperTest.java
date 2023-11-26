package com.springbatch.project.springbatch.repository;

import com.springbatch.project.springbatch.repository.pass.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PassModelMapperTest {

    @Test
    public void toPassEntityTest() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        String userid = "A1000000";

        BulkPassEntity bulkPassEntity = new BulkPassEntity();
        bulkPassEntity.setPackageSeq(1);
        bulkPassEntity.setUserGroupId("GROUP");
        bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        bulkPassEntity.setCount(10);
        bulkPassEntity.setStartedAt(now.minusDays(60));
        bulkPassEntity.setEndedAt(now);

        //when
        PassEntity passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userid);

        //then
        assertThat(passEntity.getPackageSeq()).isEqualTo(1);
        assertThat(passEntity.getStatus()).isEqualTo(PassStatus.READY);
        assertThat(passEntity.getRemainingCount()).isEqualTo(10);
        assertThat(passEntity.getStartedAt()).isEqualTo(now.minusDays(60));
        assertThat(passEntity.getEndedAt()).isEqualTo(now);
    }
}
