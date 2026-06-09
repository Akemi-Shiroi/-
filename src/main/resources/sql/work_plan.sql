CREATE TABLE IF NOT EXISTS `work_plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_name` VARCHAR(100) NOT NULL COMMENT '作业计划名称',
  `section_level_1` VARCHAR(100) DEFAULT NULL COMMENT '分部分项一级',
  `section_level_2` VARCHAR(100) DEFAULT NULL COMMENT '分部分项二级',
  `section_level_3` VARCHAR(100) DEFAULT NULL COMMENT '分部分项三级',
  `area_location` VARCHAR(200) DEFAULT NULL COMMENT '区域位置',
  `completed_percent` DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '已完成百分比',
  `current_plan_qty` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '本次计划完成数量',
  `current_plan_percent` DECIMAL(5,2) DEFAULT NULL COMMENT '本次计划完成百分比',
  `plan_start_date` DATE DEFAULT NULL COMMENT '计划开始日期',
  `plan_end_date` DATE DEFAULT NULL COMMENT '计划结束日期',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常 0-停用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作业计划主表';

CREATE TABLE IF NOT EXISTS `work_plan_resource` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_id` BIGINT NOT NULL COMMENT '作业计划ID',
  `resource_type` TINYINT NOT NULL COMMENT '资源类型：1-人 2-机 3-材',
  `category_1` VARCHAR(100) DEFAULT NULL COMMENT '分类1',
  `category_2` VARCHAR(100) DEFAULT NULL COMMENT '分类2',
  `resource_name` VARCHAR(100) DEFAULT NULL COMMENT '资源名称',
  `spec_model` VARCHAR(100) DEFAULT NULL COMMENT '规格型号',
  `unit` VARCHAR(30) DEFAULT NULL COMMENT '单位',
  `quantity` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '数量',
  `use_date` DATE DEFAULT NULL COMMENT '使用日期',
  `extra_flag` VARCHAR(30) DEFAULT NULL COMMENT '扩展标记',
  `sort_no` INT NOT NULL DEFAULT 0 COMMENT '排序号',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  CONSTRAINT `fk_work_plan_resource_plan_id`
    FOREIGN KEY (`plan_id`) REFERENCES `work_plan` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作业计划人机材明细表';
