package com.springbatch.project.springbatch.repository.pass;


import com.springbatch.project.springbatch.service.pass.Pass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

// ReportingPolicy.IGNORE: 일치하지 않는 필드는 무시
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassModelMapper {
    PassModelMapper INSTANCE = Mappers.getMapper(PassModelMapper.class); // Instance를 선언해주면 매퍼에 대한 접근이 가능, 매퍼 클래스에서 PassModelMapper를 찾을 수 있도록 해준다

    /*
     필드명이 같지 않거나 custom하게 매핑해주기 위해서는 @Mapping을 추가
     @Mapping을 이용하여 source에는 매핑값을 가지고 올 대상, target에는 매핑할 대상을 각각 작성
    */
    @Mapping(target = "status", qualifiedByName = "defaultStatus")
    @Mapping(target = "remainingCount", source = "bulkPassEntity.count")
    PassEntity toPassEntity(BulkPassEntity bulkPassEntity, String userId);  // bulkPassEntity를 PassEntity로 매핑

    // BulkPassStatus와 관계 없이 PassStatus값을 READY로 설정
    @Named("defaultStatus")
    default PassStatus status(BulkPassStatus status) {
        return PassStatus.READY;
    }

    @Mapping(target = "packageName", source = "packageEntity.packageName")
    Pass map(PassEntity passEntity);

    List<Pass> map(List<PassEntity> passEntities);
}
