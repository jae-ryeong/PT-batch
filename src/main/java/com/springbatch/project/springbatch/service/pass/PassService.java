package com.springbatch.project.springbatch.service.pass;

import com.springbatch.project.springbatch.repository.pass.PassEntity;
import com.springbatch.project.springbatch.repository.pass.PassModelMapper;
import com.springbatch.project.springbatch.repository.pass.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PassService {
    private final PassRepository passRepository;

    public List<Pass> getPasses(String userId) {
        List<PassEntity> passEntities = passRepository.findByUserId(userId);
        return PassModelMapper.INSTANCE.map(passEntities);
    }
}
