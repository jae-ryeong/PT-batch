package com.springbatch.project.springbatch.service.user;

import com.springbatch.project.springbatch.repository.user.UserGroupMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserGroupMappingService {
    private final UserGroupMappingRepository userGroupMappingRepository;

    public List<String> getAllUserGroupIds() {
        return userGroupMappingRepository.findDistinctUserGroupId();
    }
}
