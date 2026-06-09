package com.example.mytask.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AddWorkPlanRequest {

    private Long id;
    private String planName;
    private String sectionLevel1;
    private String sectionLevel2;
    private String sectionLevel3;
    private String areaLocation;
    private BigDecimal completedPercent;
    private BigDecimal currentPlanQty;
    private BigDecimal currentPlanPercent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate planStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate planEndDate;

    private String remark;
    private List<WorkPlanResourceRequest> resourceList;
}
