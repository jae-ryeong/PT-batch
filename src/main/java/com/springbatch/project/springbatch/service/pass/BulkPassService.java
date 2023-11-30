package com.springbatch.project.springbatch.service.pass;

import com.springbatch.project.springbatch.controller.admin.BulkPassRequest;
import com.springbatch.project.springbatch.repository.packaze.PackageEntity;
import com.springbatch.project.springbatch.repository.packaze.PackageRepository;
import com.springbatch.project.springbatch.repository.pass.BulkPassEntity;
import com.springbatch.project.springbatch.repository.pass.BulkPassRepository;
import com.springbatch.project.springbatch.repository.pass.BulkPassStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BulkPassService {
    private final BulkPassRepository bulkPassRepository;
    private final PackageRepository packageRepository;

    public List<BulkPass> getAllBulkPasses() {
        List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findAllOrderByStartedAtDesc();
        return BulkPassModelMapper.INSTANCE.map(bulkPassEntities);
    }

    public void addBulkPass(BulkPassRequest bulkPassRequest) {
        PackageEntity packageEntity = packageRepository.findById(bulkPassRequest.getPackageSeq()).orElseThrow();

        BulkPassEntity bulkPassEntity = BulkPassModelMapper.INSTANCE.map(bulkPassRequest);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(packageEntity.getCount());
        bulkPassEntity.setEndedAt(packageEntity.getPeriod());

        bulkPassRepository.save(bulkPassEntity);
    }
}
