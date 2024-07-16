package com.efficient.form.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.common.result.Result;
import com.efficient.form.model.dto.DynamicFormsDTO;
import com.efficient.form.model.dto.DynamicFormsListDTO;
import com.efficient.form.model.entity.DynamicForms;
import com.efficient.form.model.vo.DynamicFormsVO;

/**
 * <p>
 * 系统动态表单 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
public interface DynamicFormsService extends IService<DynamicForms> {
    /***
     * 新增
     */
    Result<DynamicForms> save(DynamicFormsDTO dto);

    /**
     * 详情
     */
    Result<DynamicFormsVO> findById(String id);

    /**
     * 修改
     */
    Result<Boolean> update(DynamicFormsDTO dto);

    /**
     * 删除
     */
    Result<Boolean> delete(String id);

    /**
     * 列表查询
     */
    Page<DynamicForms> list(DynamicFormsListDTO dto);

    /**
     * 更改表单状态
     *
     * @param id
     * @param isEnabled
     * @return
     */
    Result<Boolean> changeEnabled(String id, Integer isEnabled);

}
