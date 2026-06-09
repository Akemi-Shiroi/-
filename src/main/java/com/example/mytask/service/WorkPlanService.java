package com.example.mytask.service;

import com.example.mytask.pojo.AddWorkPlanRequest;
import com.example.mytask.pojo.WorkPlanDetailResponse;

import java.util.List;

public interface WorkPlanService {

    boolean addWorkPlan(AddWorkPlanRequest request);

    boolean updateWorkPlan(AddWorkPlanRequest request);

    boolean deleteWorkPlan(Long id);

    WorkPlanDetailResponse queryWorkPlan(Long id);

    List<WorkPlanDetailResponse> listWorkPlans();

    String ackWorkPlan(Long id);
}
