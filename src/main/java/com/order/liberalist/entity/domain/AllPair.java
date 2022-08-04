package com.order.liberalist.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("all_pair")
public class AllPair {
    /**
     * 是否有滑点，0：没有滑点， 1：有滑点
     */
    private Integer isSlip;

    /**
     * 是否执行过链路扫描(0-false 1-true)
     */
    private Integer isSleuth;

    /**
     * 池子合约
     */
    private String pair;

    /**
     * 路径1
     */
    private String path1;

    /**
     * 路径0
     */
    private String path0;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
}

