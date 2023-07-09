package com.pan.common.utils;

import com.alibaba.fastjson2.TypeReference;
import com.pan.common.dto.TreeNode;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树结构-构造工具类
 */
public class TreeUtils {


    public static List<TreeNode> toTree(List<TreeNode> treeNodeList,Integer pid) {
        //pid -> children 父id对应的 子节点集合
        Map<Integer, List<TreeNode>> mapByPid = treeNodeList.stream().collect(Collectors.groupingBy(TreeNode::getPid));
        treeNodeList.stream().forEach(treeNode -> {
            List<TreeNode> children = mapByPid.get(treeNode.getId());
            if (!CollectionUtils.isEmpty(children)){
                treeNode.setChildrenList(children);
            }
        });
        //根据pid获取子节点
        List<TreeNode> result = treeNodeList.stream().filter(treeNode -> treeNode.getPid() == pid).collect(Collectors.toList());
        return result;
    }

    public static void main(String[] args) {
        String treeJson = "[{\"id\":1,\"pid\":0,\"name\":\"安徽\"},{\"id\":2,\"pid\":1,\"name\":\"安庆\"},{\"id\":3,\"pid\":1,\"name\":\"芜湖\"},{\"id\":4,\"pid\":1,\"name\":\"马鞍山\"},{\"id\":5,\"pid\":4,\"name\":\"和县\"},{\"id\":6,\"pid\":5,\"name\":\"白桥\"},{\"id\":7,\"pid\":5,\"name\":\"老桥\"},{\"id\":8,\"pid\":3,\"name\":\"沈巷\"},{\"id\":9,\"pid\":3,\"name\":\"大任桥\"},{\"id\":10,\"pid\":0,\"name\":\"江苏\"}]";
        List<TreeNode> treeNodes = FastJsonUtils.json2Obj(treeJson, new TypeReference<List<TreeNode>>() {
        });
        List<TreeNode> result = toTree(treeNodes,0);
        System.out.println(result);
    }
}
