package com.order.liberalist.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pair_sleuth")
public class PairSleuth {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 路径0
     */
    private String path1;

    /**
     * 链路组(英文逗号分隔)
     */
    private String sleuth;

    /**
     * pairId
     */
    private Integer pairId;
}

