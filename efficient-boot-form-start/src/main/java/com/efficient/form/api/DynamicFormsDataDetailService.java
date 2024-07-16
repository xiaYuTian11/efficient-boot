package com.efficient.form.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.form.model.entity.DynamicFormsDataDetail;

import java.util.List;

/**
 * <p>
 * 系统动态表单-表单数据-详细 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
public interface DynamicFormsDataDetailService extends IService<DynamicFormsDataDetail> {

    /**
     * 保存详情信息
     */
    void saveByDataId(String formsId,String dataId, List<DynamicFormsDataDetail> dataDetailList);

    /**
     * 删除
     *
     * @param dataId
     */
    void deleteByDataId(String dataId);

    /**
     * 查询
     *
     * @param dataId
     * @return
     */
    List<DynamicFormsDataDetail> findByDataId(String dataId);
}
