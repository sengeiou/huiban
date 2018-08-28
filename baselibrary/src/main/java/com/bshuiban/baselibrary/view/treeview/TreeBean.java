package com.bshuiban.baselibrary.view.treeview;

/**
 * Created by xinheng on 2018/8/21.<br/>
 * describe：树形Bean类
 * 第一层级为1
 */
public class TreeBean {
    /**
     * 第一层级
     */
    public static final int FIRST_RANK=1;
    /**
     * 第几层级
     */
    private int rank;
    /**
     * 是否含有子节点
     */
    private boolean isHaveChildNode;
    /**
     * 子节点数
     * （仅仅下一层级）
     */
    private int childNodeNum;
    /**
     * 此节点包含的信息
     */
    private Object text;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isHaveChildNode() {
        return isHaveChildNode;
    }

    public void setHaveChildNode(boolean haveChildNode) {
        isHaveChildNode = haveChildNode;
    }

    public int getChildNodeNum() {
        return childNodeNum;
    }

    public void setChildNodeNum(int childNodeNum) {
        this.childNodeNum = childNodeNum;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}
