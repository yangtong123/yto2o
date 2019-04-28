package com.yt.enums;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/25 22:37
 */
public enum ShopStateEnum {
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部错误"),
    NULL_SHOPID(-1002, "ShopId为空"),
    NULL_SHOP(-1003, "Shop信息为空");


    private int state;
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 根据传入的 state 返回相应的 enum
     * @param state
     * @return
     */
    public static ShopStateEnum statOf(int state) {
        for (ShopStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }

        return null;
    }


    public int getState() {
        return state;
    }


    public String getStateInfo() {
        return stateInfo;
    }

}
