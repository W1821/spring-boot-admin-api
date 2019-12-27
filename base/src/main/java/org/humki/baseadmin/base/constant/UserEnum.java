package org.humki.baseadmin.base.constant;

import lombok.Getter;

/**
 * <br>
 * <b>功能：</b><br>
 *
 * <b>日期：</b>2017/6/19 16:14<br>
 *
 * @author Kael
 */
public final class UserEnum {

    /**
     * 是否是超级管理员，0：否，1：是
     */
    public enum SUPER_ADMIN {

        /**
         * 否
         */
        NO(0, "否"),
        /**
         * 是
         */
        YES(1, "是");

        @Getter
        private int key;
        @Getter
        private String value;

        SUPER_ADMIN(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}
