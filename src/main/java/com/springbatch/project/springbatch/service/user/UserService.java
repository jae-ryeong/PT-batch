package com.springbatch.project.springbatch.service.user;

import com.springbatch.project.springbatch.repository.user.UserEntity;
import com.springbatch.project.springbatch.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        // userId로 사용자 정보 조회, 프로필에 노출할 사용자 이름이 필요
        UserEntity userEntity = userRepository.findByUserId(userId);
        return UserModelMapper.INSTANCE.toUser(userEntity);
    }
}
