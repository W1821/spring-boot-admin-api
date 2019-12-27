package org.humki.baseadmin.common.annotation;

import java.lang.annotation.*;

/**
 * 菜单权限注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface HandlerAuth {

    /**
     * 是否验证URL true=验证 ,false = 不验证
     */
    boolean verify() default false;

}