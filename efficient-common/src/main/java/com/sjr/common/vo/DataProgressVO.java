package com.sjr.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataProgressVO {

    /**
     * 当前数据描述
     */
    private String currDataName;
    /**
     * 当前进度
     */
    private String ratio;
    /**
     * 数据列表
     */
    private Object data;
    /**
     * 进行状态 1:进行中，2:成功，3:失败
     */
    private String code;

}
