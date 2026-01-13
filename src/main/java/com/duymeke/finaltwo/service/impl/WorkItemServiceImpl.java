package com.duymeke.finaltwo.service.impl;


import com.duymeke.finaltwo.dto.LabelDto;
import com.duymeke.finaltwo.dto.WorkItemDto;
import com.duymeke.finaltwo.entity.Account;
import com.duymeke.finaltwo.entity.WorkItem;
import com.duymeke.finaltwo.mapper.WorkItemMapper;
import com.duymeke.finaltwo.repository.AccountRepository;
import com.duymeke.finaltwo.repository.LabelRepository;
import com.duymeke.finaltwo.repository.WorkItemRepository;
import com.duymeke.finaltwo.service.WorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkItemServiceImpl implements WorkItemService {

    private final WorkItemRepository repository;
    private final AccountRepository accountRepository;
    private final LabelRepository labelRepository;
    private final WorkItemMapper mapper;

    @Override
    public List<WorkItemDto> getAll(Long accountId) {
        if (accountId == null) return List.of();
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) return List.of();
        return mapper.toDtoList(repository.findByAccount(account));
    }

    @Override
    public WorkItemDto getById(Long id, Long accountId) {
        WorkItem item = repository.findById(id).orElse(null);
        if (item == null) return null;

        if (accountId != null && item.getAccount() != null &&
                !item.getAccount().getId().equals(accountId)) {
            return null;
        }
        return mapper.toDto(item);
    }

    @Override
    public WorkItemDto create(WorkItemDto dto) {
        if (dto == null) return null;

        WorkItem item = mapper.toEntity(dto);
        Account account = accountRepository.findById(dto.getAccountId()).orElse(null);
        item.setAccount(account);

        if (dto.getCategories() != null) {
            item.setCategories(
                    new ArrayList<>(
                            dto.getCategories().stream()
                                    .map(LabelDto::getId)
                                    .map(labelRepository::findById)
                                    .flatMap(java.util.Optional::stream)
                                    .toList()
                    )
            );
        }
        return mapper.toDto(repository.save(item));
    }

    @Override
    public WorkItemDto update(Long id, WorkItemDto dto, Long accountId) {
        WorkItem item = repository.findById(id).orElse(null);
        if (item == null || dto == null) return null;

        if (accountId != null && item.getAccount() != null &&
                !item.getAccount().getId().equals(accountId)) {
            return null;
        }

        item.setTitle(dto.getTitle());
        item.setText(dto.getText());
        item.setCompleted(dto.isCompleted());

        if (dto.getCategories() != null) {
            item.setCategories(
                    new ArrayList<>(
                            dto.getCategories().stream()
                                    .map(LabelDto::getId)
                                    .map(labelRepository::findById)
                                    .flatMap(java.util.Optional::stream)
                                    .toList()
                    )
            );
        }
        return mapper.toDto(repository.save(item));
    }

    @Override
    public boolean delete(Long id, Long accountId) {
        WorkItem item = repository.findById(id).orElse(null);
        if (item == null) return false;

        if (accountId != null && item.getAccount() != null &&
                !item.getAccount().getId().equals(accountId)) {
            return false;
        }

        repository.deleteById(id);
        return Objects.isNull(getById(id, accountId));
    }
}

