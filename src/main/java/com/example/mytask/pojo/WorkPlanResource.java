package com.example.mytask.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkPlanResource {

    private Long id;
    private Long planId;
    private Integer resourceType;
    private String category1;
    private String category2;
    private String resourceName;
    private String specModel;
    private String unit;
    private BigDecimal quantity;
    private LocalDate useDate;
    private String extraFlag;
    private Integer sortNo;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
