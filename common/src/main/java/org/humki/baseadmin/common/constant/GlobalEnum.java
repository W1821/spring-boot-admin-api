package org.humki.baseadmin.common.constant;

import lombok.Getter;

/**
 * <br>
 * <b>功能：</b>公共枚举类<br>

 * <b>日期：</b>2017/4/11 23:27<br>
 * @author Kael
 */
public final class GlobalEnum {

    /**
     * 密码加密方式
     */
    public enum ENCODING_ID {
        /**
         * 未删除
         */
        BCrypt("bcrypt"),
        LdapSha("ldap"),
        MD4("MD4"),
        MD5("MD5"),
        NoOp("noop"),
        Pbkdf2("pbkdf2"),
        SCrypt("scrypt"),
        SHA1("SHA-1"),
        SHA256("SHA-256"),
        sha256("sha256");

        @Getter
        private String value;

        ENCODING_ID( String value) {
            this.value = value;
        }

    }

    /**
     * 状态，0：启用，1：禁用
     */
    public enum DISABLED {

        /**
         * 状态启用
         */
        NO(0, "启用"),
        /**
         * 状态禁用
         */
        YES(1, "禁用");

        @Getter
        private int key;
        @Getter
        private String value;

        DISABLED(int key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    /**
     * 删除状态
     */
    public enum DELETED {
        /**
         * 未删除
         */
        NO(0, "N"),
        /**
         * 已删除
         */
        YES(1, "Y");

        @Getter
        private int key;
        @Getter
        private String value;

        DELETED(int key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    /**
     * 图片格式
     */
    public enum IMAGE_FIELDS {

        /**
         * png图片
         */
        PNG(".png"),
        JPEG(".jpg"),
        JPG(".jpeg"),
        GIF(".gif");

        @Getter
        private String value;

        IMAGE_FIELDS(String value) {
            this.value = value;
        }

        public static boolean containsIgnoreCase(String imageField) {
            if (imageField == null) {
                return false;
            }
            for (IMAGE_FIELDS e : IMAGE_FIELDS.values()) {
                if (imageField.equalsIgnoreCase(e.getValue())) {
                    return true;
                }
            }
            return false;
        }

    }
}
