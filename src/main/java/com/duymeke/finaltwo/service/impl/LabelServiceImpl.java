package com.duymeke.finaltwo.service.impl;



import com.duymeke.finaltwo.dto.LabelDto;
import com.duymeke.finaltwo.entity.Label;
import com.duymeke.finaltwo.mapper.LabelMapper;
import com.duymeke.finaltwo.repository.LabelRepository;
import com.duymeke.finaltwo.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;
    private final LabelMapper mapper;

    @Override
    public List<LabelDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public LabelDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public LabelDto create(LabelDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public LabelDto update(Long id, LabelDto dto) {
        Label old = repository.findById(id).orElse(null);
        if (Objects.isNull(dto) || Objects.isNull(old)) {
            return null;
        }
        old.setName(dto.getName());
        return mapper.toDto(repository.save(old));
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        LabelDto deleted = getById(id);
        return Objects.isNull(deleted);
    }
}
