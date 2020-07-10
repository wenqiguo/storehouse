package com.nylg.gwq.common;


import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    public static List<TreeNodes> build(List<TreeNodes> treeNodes,Integer topId){
        List<TreeNodes> nodes = new ArrayList<>();
        //第一层遍历所有父节点为topId的
        //第二层遍历是在遍历时放入第一个数在nodes时，找到该结点的子节点，把它放进子节点中
      for (TreeNodes n1: treeNodes){
          if (n1.getPid()==topId){
              nodes.add(n1);
          }
          for (TreeNodes n2 : treeNodes){
              if (n1.getId()==n2.getPid()){
                 n1.getChildren().add(n2);
              }
          }
      }
      return nodes;
    }
}
