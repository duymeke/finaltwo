package com.duymeke.finaltwo.mapper;

import com.duymeke.finaltwo.dto.WorkItemDto;
import com.duymeke.finaltwo.entity.WorkItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LabelMapper.class})
public interface WorkItemMapper {

    @Mapping(source = "account.id", target = "accountId")
    WorkItemDto toDto(WorkItem workItem);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "labels", ignore = true)
    WorkItem toEntity(WorkItemDto dto);

    List<WorkItemDto> toDtoList(List<WorkItem> workItems);
}

