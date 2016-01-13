package com.bluelable.bluelog.bean;

/**
 * Created by xinspace on 12/31/15.
 * 记录崩溃事件的具体内容
 */
public class CrashEventValue {

    //事件发生的包名
    private String pkgName;
    //崩溃的原因
    private String error;

    //getters and setters
    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
