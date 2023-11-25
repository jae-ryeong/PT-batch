package com.springbatch.project.springbatch.config;

import com.springbatch.project.springbatch.repository.pass.*;
import com.springbatch.project.springbatch.repository.user.UserGroupMappingEntity;
import com.springbatch.project.springbatch.repository.user.UserGroupMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AddPassesTasklet implements Tasklet {

    private final PassRepository passRepository;
    private final BulkPassRepository bulkPassRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 이용권 시작 일시 1일 전 user group 내 각 사용자에게 이용권을 추가해준다.
        final LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        // 아직 시작하지 않은
        final List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        int count = 0;

        for (BulkPassEntity bulkPassEntity : bulkPassEntities){
            // 아직 시작하지 않은 user group에 속한 userId들을 조회한다.
            final List<String> userIds = userGroupMappingRepository.findByUserGroupId(bulkPassEntity.getUserGroupId())
                    .stream().map(UserGroupMappingEntity::getUserId).toList();

            // 이용권 몇 건이 추가 되었는지 -> 몇 건의 데이터가 처리 되었는지
            count += addPasses(bulkPassEntity, userIds);
            // pass 추가 이후 상태를 COMPLETED로 업데이트
            bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        }
        log.info("AddPassesTasklet - excute: 이용권 {}건 추가 완료, startedAt={}", count, startedAt);
        return RepeatStatus.FINISHED;   // 작업이 끝났다.
    }

    // bulkPass의 정보로 pass 데이터를 생성
    private int addPasses(BulkPassEntity bulkPassEntity, List<String> userIds) {
        List<PassEntity> passEntities = new ArrayList<>();

        for (String userId : userIds) {
            // bulkPassEntity -> passEntity
            PassEntity passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);
            passEntities.add(passEntity);
        }
        // 같은 그룹 내의 유저들을 개별적으로 pass에 저장
        return passRepository.saveAll(passEntities).size();
    }
}
