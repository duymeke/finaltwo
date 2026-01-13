package com.duymeke.finaltwo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemDto {
    private Long id;
    private String title;
    private String text;
    private boolean completed;
    private Long accountId;
    private List<LabelDto> categories;
}
