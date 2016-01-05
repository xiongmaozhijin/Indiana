package com.example.liangge.indiana.model.inner;

/**
 * 返回的类别实体
 * Created by baoxing on 2016/1/4.
 */
public class IndianaCategoryEntity {

    private long category_id;

    private String category_name;

    private String icon;


    public IndianaCategoryEntity() {
    }


    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    @Override
    public String toString() {
        return "IndianaCategoryEntity{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
