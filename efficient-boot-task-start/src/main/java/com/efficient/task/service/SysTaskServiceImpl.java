package com.efficient.task.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.task.api.SysTaskService;
import com.efficient.task.model.converter.SysTaskConverter;
import com.efficient.task.dao.SysTaskMapper;
import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.dto.SysTaskListDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author code generator
 * @date 2022-08-28 18:08:05
 */
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements SysTaskService {

    @Autowired
    private SysTaskConverter sysTaskConverter;
    @Autowired
    private SysTaskMapper sysTaskMapper;

    @Override
    public SysTask save(SysTaskDTO dto) {
        SysTask entity = sysTaskConverter.dto2Entity(dto);
        boolean flag = this.save(entity);
        return entity;
    }

    @Override
    public SysTaskVO findById(String id) {
        SysTask entity = this.getById(id);
        return sysTaskConverter.entity2Vo(entity);
    }

    @Override
    public Boolean update(SysTaskDTO dto) {
        return this.updateById(sysTaskConverter.dto2Entity(dto));
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public Page<SysTask> list(SysTaskListDTO dto) {
        final Page<SysTask> page = sysTaskMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), new QueryWrapper<>());
        return page;
    }

    @Override
    public List<SysTask> findAll() {
        LambdaQueryWrapper<SysTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTask::getEnabled, 1);
        return this.list(queryWrapper);
    }
}
