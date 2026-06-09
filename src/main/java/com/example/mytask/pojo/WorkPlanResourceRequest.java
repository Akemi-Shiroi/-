package com.example.mytask.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WorkPlanResourceRequest {

    private Integer resourceType;
    private String category1;
    private String category2;
    private String resourceName;
    private String specModel;
    private String unit;
    private BigDecimal quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate useDate;

    private String extraFlag;
    private Integer sortNo;
    private String remark;
}
