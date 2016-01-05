package com.example.liangge.indiana.model.inner;

/**
 * 类别具体实体
 * Created by baoxing on 2016/1/4.
 */
public class CategoryDetailEntitiy {

    /*期次*/
    private long issue_id;

    /*商品id*/
    private long commodity_id;

    /*描述*/
    private String name;

    /*总需*/
    private int  share_total;

    /*当前已购买*/
    private int  share_current;

    /*图片地址*/
    private String icon;


    public CategoryDetailEntitiy() {
    }


    public long getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(long issue_id) {
        this.issue_id = issue_id;
    }

    public long getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(long commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShare_total() {
        return share_total;
    }

    public void setShare_total(int share_total) {
        this.share_total = share_total;
    }

    public int getShare_current() {
        return share_current;
    }

    public void setShare_current(int share_current) {
        this.share_current = share_current;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    @Override
    public String toString() {
        return "CategoryDetailEntitiy{" +
                "issue_id=" + issue_id +
                ", commodity_id=" + commodity_id +
                ", name='" + name + '\'' +
                ", share_total=" + share_total +
                ", share_current=" + share_current +
                ", icon='" + icon + '\'' +
                '}';
    }
}
