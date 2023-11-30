package com.springbatch.project.springbatch.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Integer> {
    List<UserGroupMappingEntity> findByUserGroupId(String userGroupId);

    @Query("select distinct u.userGroupId " +
            " from UserGroupMappingEntity  u " +
            " order by u.userGroupId")
    List<String> findDistinctUserGroupId();
}
