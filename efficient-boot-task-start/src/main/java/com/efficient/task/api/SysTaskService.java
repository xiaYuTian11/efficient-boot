package com.efficient.task.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.common.result.Result;
import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.dto.SysTaskListDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 定时任务表 服务Api
 * </p>
 *
 * @author TMW
 * @date 2022-08-28 18:08:05
 */
public interface SysTaskService extends IService<SysTask> {
    /***
     * 新增
     */
    Result<SysTask> save(SysTaskDTO dto);
    SysTask findByCode(String code);

    /**
     * 详情
     */
    SysTaskVO findById(String id);

    /**
     * 修改
     */
    Result<Boolean> update(SysTaskDTO dto);

    /**
     * 删除
     */
    Boolean delete(String id);

    /**
     * 列表查询
     */
    Page<SysTask> list(SysTaskListDTO dto);

    /**
     * 查找所有定时任务
     *
     * @return
     */
    List<SysTask> findAll();

}
