package com.haozi.algorithm.model;

/**
 * @author hao.yang
 * @date 2019/10/10
 */
public class TreeNode {

    private TreeNode leftNode; // 左节点

    private TreeNode rightNode; // 有节点

    private int value; // 数据

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
