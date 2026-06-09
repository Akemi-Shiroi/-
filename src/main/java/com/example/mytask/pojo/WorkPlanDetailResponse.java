package com.example.mytask.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkPlanDetailResponse {

    private Long id;
    private String planName;
    private String sectionLevel1;
    private String sectionLevel2;
    private String sectionLevel3;
    private String areaLocation;
    private BigDecimal completedPercent;
    private BigDecimal currentPlanQty;
    private BigDecimal currentPlanPercent;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private String remark;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WorkPlanResource> resourceList;
}
