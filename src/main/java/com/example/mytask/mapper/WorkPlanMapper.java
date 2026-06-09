package com.example.mytask.mapper;

import com.example.mytask.pojo.WorkPlan;
import com.example.mytask.pojo.WorkPlanDetailResponse;
import com.example.mytask.pojo.WorkPlanResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkPlanMapper {

    int insertWorkPlan(WorkPlan workPlan);

    int insertWorkPlanStatus(@Param("workPlanId") Long workPlanId, @Param("status") Integer status);

    int updateWorkPlan(WorkPlan workPlan);

    int deleteResourcesByPlanId(Long planId);

    int deleteWorkPlanById(Long id);

    WorkPlanDetailResponse selectWorkPlanById(Long id);

    List<WorkPlanDetailResponse> selectAllWorkPlans();

    Integer selectWorkPlanStatusByPlanId(Long workPlanId);

    int updateWorkPlanStatusByPlanId(@Param("workPlanId") Long workPlanId, @Param("status") Integer status);

    List<WorkPlanResource> selectResourcesByPlanId(Long planId);

    int batchInsertResources(@Param("resourceList") List<WorkPlanResource> resourceList);
}
