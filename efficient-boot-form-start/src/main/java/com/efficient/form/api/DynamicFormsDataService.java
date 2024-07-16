package com.efficient.form.api;

import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.form.model.dto.DynamicFormsDataDTO;
import com.efficient.form.model.dto.DynamicFormsDataListDTO;
import com.efficient.form.model.entity.DynamicFormsData;
import com.efficient.form.model.vo.DynamicFormsDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* <p>
* 系统动态表单-表单数据 服务Api
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
public interface DynamicFormsDataService extends IService<DynamicFormsData> {
    /***
    * 新增
    */
    Result<DynamicFormsData> save(DynamicFormsDataDTO dto);

    /**
    * 详情
    */
    Result<DynamicFormsDataVO> findById(String id);

    /**
    * 修改
    */
    Result<Boolean> update(DynamicFormsDataDTO dto);

    /**
    * 删除
    */
    Result<Boolean> delete(String id);

    /**
    * 列表查询
    */
    Page<Map<String, Object>> list(DynamicFormsDataListDTO dto);
}
