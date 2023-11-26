package com.springbatch.project.springbatch.repository;

import com.springbatch.project.springbatch.config.AddPassesTasklet;
import com.springbatch.project.springbatch.repository.pass.*;
import com.springbatch.project.springbatch.repository.user.UserGroupMappingEntity;
import com.springbatch.project.springbatch.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AddPassesTaskletTest {
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private PassRepository passRepository;
    @Mock
    private BulkPassRepository bulkPassRepository;
    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    @InjectMocks
    private AddPassesTasklet addPassesTasklet;

    @Test
    public void executeTest() throws Exception{
        //given
        String userGroupId = "GROUP";
        String userId = "A10000000";
        Integer packageSeq = 1;
        Integer count = 10;

        LocalDateTime now = LocalDateTime.now();

        BulkPassEntity bulkPassEntity = new BulkPassEntity();
        bulkPassEntity.setPackageSeq(packageSeq);
        bulkPassEntity.setUserGroupId(userGroupId);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(count);
        bulkPassEntity.setStartedAt(now);
        bulkPassEntity.setEndedAt(now.plusDays(60));

        UserGroupMappingEntity userGroupMappingEntity = new UserGroupMappingEntity();
        userGroupMappingEntity.setUserGroupId(userGroupId);
        userGroupMappingEntity.setUserId(userId);

        // when
        when(bulkPassRepository.findByStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any())).thenReturn(List.of(bulkPassEntity));
        when(userGroupMappingRepository.findByUserGroupId(eq("GROUP"))).thenReturn(List.of(userGroupMappingEntity));

        RepeatStatus repeatStatus = addPassesTasklet.execute(stepContribution, chunkContext);

        //then
        assertThat(repeatStatus).isEqualTo(RepeatStatus.FINISHED);

        // 중간에 어떤 인자 값이 들어가는지 알 수 있게 ArgumentCaptor사용
        ArgumentCaptor<List> passEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(passRepository, times(1)).saveAll(passEntitiesCaptor.capture());
        List<PassEntity> passEntities = passEntitiesCaptor.getValue();

        assertThat(passEntities.size()).isEqualTo(1);
        PassEntity passEntity = passEntities.get(0);
        assertThat(passEntity.getPackageSeq()).isEqualTo(packageSeq);
        assertThat(passEntity.getUserId()).isEqualTo(userId);
        assertThat(passEntity.getStatus()).isEqualTo(PassStatus.READY);
        assertThat(passEntity.getRemainingCount()).isEqualTo(count);
    }
}
