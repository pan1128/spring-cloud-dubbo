package com.pan.common.dto;

import lombok.Data;

@Data
public class BaseSearchDTO {
    private Integer pageSize = 10;
    private Integer pageNum = 1;
}
