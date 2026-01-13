package com.duymeke.finaltwo.mapper;

import com.duymeke.finaltwo.dto.LabelDto;
import com.duymeke.finaltwo.entity.Label;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    LabelDto toDto(Label label);
    Label toEntity(LabelDto dto);
    List<LabelDto> toDtoList(List<Label> labels);
}
