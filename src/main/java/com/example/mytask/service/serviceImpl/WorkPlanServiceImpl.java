package com.example.mytask.service.serviceImpl;

import com.example.mytask.mapper.WorkPlanMapper;
import com.example.mytask.pojo.AddWorkPlanRequest;
import com.example.mytask.pojo.WorkPlan;
import com.example.mytask.pojo.WorkPlanDetailResponse;
import com.example.mytask.pojo.WorkPlanResource;
import com.example.mytask.pojo.WorkPlanResourceRequest;
import com.example.mytask.service.WorkPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkPlanServiceImpl implements WorkPlanService {

    private final WorkPlanMapper workPlanMapper;

    public WorkPlanServiceImpl(WorkPlanMapper workPlanMapper) {
        this.workPlanMapper = workPlanMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addWorkPlan(AddWorkPlanRequest request) {
        WorkPlan workPlan = new WorkPlan();
        fillWorkPlan(workPlan, request);
        workPlan.setStatus(1);

        int inserted = workPlanMapper.insertWorkPlan(workPlan);
        if (inserted <= 0 || workPlan.getId() == null) {
            throw new IllegalStateException("insert work plan failed");
        }

        int statusInserted = workPlanMapper.insertWorkPlanStatus(workPlan.getId(), 0);
        if (statusInserted <= 0) {
            throw new IllegalStateException("insert work plan status failed");
        }

        List<WorkPlanResource> resourceList = buildResourceList(request.getResourceList(), workPlan.getId());
        if (resourceList.isEmpty()) {
            return true;
        }

        int resourceInserted = workPlanMapper.batchInsertResources(resourceList);
        if (resourceInserted != resourceList.size()) {
            throw new IllegalStateException("insert work plan resources failed");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWorkPlan(AddWorkPlanRequest request) {
        if (request.getId() == null) {
            return false;
        }

        WorkPlan workPlan = new WorkPlan();
        workPlan.setId(request.getId());
        fillWorkPlan(workPlan, request);

        int updated = workPlanMapper.updateWorkPlan(workPlan);
        if (updated <= 0) {
            return false;
        }

        workPlanMapper.deleteResourcesByPlanId(request.getId());

        List<WorkPlanResource> resourceList = buildResourceList(request.getResourceList(), request.getId());
        if (resourceList.isEmpty()) {
            return true;
        }

        int resourceInserted = workPlanMapper.batchInsertResources(resourceList);
        if (resourceInserted != resourceList.size()) {
            throw new IllegalStateException("update work plan resources failed");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWorkPlan(Long id) {
        if (id == null) {
            return false;
        }
        return workPlanMapper.deleteWorkPlanById(id) > 0;
    }

    @Override
    public WorkPlanDetailResponse queryWorkPlan(Long id) {
        if (id == null) {
            return null;
        }

        WorkPlanDetailResponse detail = workPlanMapper.selectWorkPlanById(id);
        if (detail == null) {
            return null;
        }

        Integer status = workPlanMapper.selectWorkPlanStatusByPlanId(id);
        detail.setStatus(status);
        detail.setResourceList(workPlanMapper.selectResourcesByPlanId(id));
        return detail;
    }

    @Override
    public List<WorkPlanDetailResponse> listWorkPlans() {
        List<WorkPlanDetailResponse> workPlanList = workPlanMapper.selectAllWorkPlans();
        for (WorkPlanDetailResponse workPlan : workPlanList) {
            workPlan.setStatus(workPlanMapper.selectWorkPlanStatusByPlanId(workPlan.getId()));
            workPlan.setResourceList(workPlanMapper.selectResourcesByPlanId(workPlan.getId()));
        }
        return workPlanList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ackWorkPlan(Long id) {
        if (id == null) {
            return "ack error";
        }

        Integer status = workPlanMapper.selectWorkPlanStatusByPlanId(id);
        if (status == null) {
            return "ack error";
        }
        if (status == 1) {
            return "already completed";
        }

        int updated = workPlanMapper.updateWorkPlanStatusByPlanId(id, 1);
        if (updated <= 0) {
            return "ack error";
        }
        return "success";
    }

    private void fillWorkPlan(WorkPlan workPlan, AddWorkPlanRequest request) {
        workPlan.setPlanName(request.getPlanName());
        workPlan.setSectionLevel1(request.getSectionLevel1());
        workPlan.setSectionLevel2(request.getSectionLevel2());
        workPlan.setSectionLevel3(request.getSectionLevel3());
        workPlan.setAreaLocation(request.getAreaLocation());
        workPlan.setCompletedPercent(request.getCompletedPercent());
        workPlan.setCurrentPlanQty(request.getCurrentPlanQty());
        workPlan.setCurrentPlanPercent(request.getCurrentPlanPercent());
        workPlan.setPlanStartDate(request.getPlanStartDate());
        workPlan.setPlanEndDate(request.getPlanEndDate());
        workPlan.setRemark(request.getRemark());
    }

    private List<WorkPlanResource> buildResourceList(List<WorkPlanResourceRequest> requestList, Long planId) {
        List<WorkPlanResource> resourceList = new ArrayList<>();
        if (requestList == null || requestList.isEmpty()) {
            return resourceList;
        }

        for (int i = 0; i < requestList.size(); i++) {
            WorkPlanResourceRequest item = requestList.get(i);
            WorkPlanResource resource = new WorkPlanResource();
            resource.setPlanId(planId);
            resource.setResourceType(item.getResourceType());
            resource.setCategory1(item.getCategory1());
            resource.setCategory2(item.getCategory2());
            resource.setResourceName(item.getResourceName());
            resource.setSpecModel(item.getSpecModel());
            resource.setUnit(item.getUnit());
            resource.setQuantity(item.getQuantity());
            resource.setUseDate(item.getUseDate());
            resource.setExtraFlag(item.getExtraFlag());
            resource.setSortNo(item.getSortNo() == null ? i + 1 : item.getSortNo());
            resource.setRemark(item.getRemark());
            resourceList.add(resource);
        }
        return resourceList;
    }
}
