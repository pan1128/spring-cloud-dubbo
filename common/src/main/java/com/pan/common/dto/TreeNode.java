package com.pan.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {

    /**
     * 父id
     */
    private Integer pid;

    /**
     * id
     */
    private Integer id;

    /**
     * 显示名称
     */
    private String name;


    /**
     * 子节点集合
     */
    private List<TreeNode> childrenList=new ArrayList<>();
}
