package com.ljw.test.vo;

import com.ljw.test.pojo.LjwDvd;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/11/1 16:01
 */
public class DvdVO extends LjwDvd {
    /**
     * 类型名
     */
    private String typeName;
    /**
     * 借出时间
     */
    private String lendDate;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }
}
