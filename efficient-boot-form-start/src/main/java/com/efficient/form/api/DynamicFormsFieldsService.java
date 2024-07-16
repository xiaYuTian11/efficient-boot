package com.efficient.form.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.form.model.entity.DynamicFormsFields;

import java.util.List;

/**
 * <p>
 * 系统动态表单-字段配置 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
public interface DynamicFormsFieldsService extends IService<DynamicFormsFields> {

    /**
     * 根据表单查询字段
     */
    List<DynamicFormsFields> findByFormsId(String formsId);

    /**
     * 保存表单数据
     */
    void saveByFormsId(String formsId, List<DynamicFormsFields> formsFieldsList);

    void deleteByFormsId(String formsId);
}
