package com.springbatch.project.springbatch.service.packaze;

import com.springbatch.project.springbatch.repository.packaze.PackageEntity;
import com.springbatch.project.springbatch.repository.packaze.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackageService {
    private final PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        List<PackageEntity> bulkPassEntities = packageRepository.findAllByOrderByPackageName();
        return PackageModelMapper.INSTANCE.map(bulkPassEntities);
    }
}
