package com.example.mytask.controller;

import com.example.mytask.pojo.AddWorkPlanRequest;
import com.example.mytask.pojo.Result;
import com.example.mytask.pojo.WorkPlanDetailResponse;
import com.example.mytask.service.WorkPlanService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class WorkPlanController {

    private final WorkPlanService workPlanService;
    private final StringRedisTemplate stringRedisTemplate;

    public WorkPlanController(WorkPlanService workPlanService, StringRedisTemplate stringRedisTemplate) {
        this.workPlanService = workPlanService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/add")
    public Result<String> addWorkPlan(@RequestBody AddWorkPlanRequest request) {
        try {
            boolean success = workPlanService.addWorkPlan(request);
            if (success) {
                return Result.success("success");
            }
        } catch (Exception exception) {
            return Result.error("add error");
        }
        return Result.error("add error");
    }

    @PostMapping("/update")
    public Result<String> updateWorkPlan(@RequestBody AddWorkPlanRequest request) {
        try {
            boolean success = workPlanService.updateWorkPlan(request);
            if (success) {
                return Result.success("success");
            }
        } catch (Exception exception) {
            return Result.error("update error");
        }
        return Result.error("update error");
    }

    @PostMapping("/delete/{id}")
    public Result<String> deleteWorkPlan(@PathVariable Long id) {
        try {
            boolean success = workPlanService.deleteWorkPlan(id);
            if (success) {
                return Result.success("success");
            }
        } catch (Exception exception) {
            return Result.error("delete error");
        }
        return Result.error("delete error");
    }

    @RequestMapping(value = "/query/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<WorkPlanDetailResponse> queryWorkPlan(@PathVariable Long id) {
        try {
            WorkPlanDetailResponse detail = workPlanService.queryWorkPlan(id);
            if (detail != null) {
                return Result.success(detail);
            }
        } catch (Exception exception) {
            return Result.fail("query error");
        }
        return Result.fail("query error");
    }

    @RequestMapping(value = "/listquery", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<WorkPlanDetailResponse>> listWorkPlans() {
        try {
            return Result.success(workPlanService.listWorkPlans());
        } catch (Exception exception) {
            return Result.fail("list query error");
        }
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> testRedis() {
        String key = "test";
        String value = stringRedisTemplate.opsForValue().get(key);
        if ("haha".equals(value)) {
            stringRedisTemplate.delete(key);
            return Result.success("delete success");
        }
        stringRedisTemplate.opsForValue().set(key, "haha");
        return Result.success("insert success");
    }

    @PostMapping("/ack/{id}")
    public Result<String> ackWorkPlan(@PathVariable Long id) {
        try {
            String result = workPlanService.ackWorkPlan(id);
            if ("success".equals(result)) {
                return Result.success("success");
            }
            if ("already completed".equals(result)) {
                return Result.success("already completed");
            }
        } catch (Exception exception) {
            return Result.error("ack error");
        }
        return Result.error("ack error");
    }
}
