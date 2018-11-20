package com.neusoft.pm.common.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 外语水平枚举
 * @Author Created by junwei.liang on 2018/11/19 13:56
 */
public enum EnglishLevelEnum implements EnumValue {
    LEVEL_0(0, "完全不懂"),
    LEVEL_1(1, "少量，不能进行业务沟通"),
    LEVEL_2(2, "有限的业务沟通"),
    LEVEL_3(3, "一般，业务沟通仍受少量限制"),
    LEVEL_4(4, "好， 无困难地进行谈判和讲演"),
    LEVEL_5(5, "流利，在商务中自如地运用");
    private int code;
    private String name;

    EnglishLevelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据code获取枚举名称
     *
     * @param code
     * @return
     */
    public static String getEnumByCode(int code) {
        for (EnglishLevelEnum itemEnum : EnglishLevelEnum.values()) {
            if (itemEnum.getCode() == code) {
                return itemEnum.getName();
            }
        }
        return null;
    }

    /**
     * 获取枚举列表
     *
     * @return
     */
    public static List<EnglishLevelEnum> getEnumList() {
        List<EnglishLevelEnum> list = new ArrayList<>(6);
        Collections.addAll(list, EnglishLevelEnum.values());
        return list;
    }
}
