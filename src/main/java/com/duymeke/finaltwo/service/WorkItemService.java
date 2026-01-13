package com.duymeke.finaltwo.service;

import com.duymeke.finaltwo.dto.WorkItemDto;

import java.util.List;

public interface WorkItemService {
    List<WorkItemDto> getAll(Long accountId);
    WorkItemDto getById(Long id, Long accountId);
    WorkItemDto create(WorkItemDto dto);
    WorkItemDto update(Long id, WorkItemDto dto, Long accountId);
    boolean delete(Long id, Long accountId);
}
