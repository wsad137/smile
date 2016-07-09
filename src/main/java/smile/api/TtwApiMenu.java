package smile.api;

import java.lang.annotation.*;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-02
 */
@Documented //文档
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取
@Target({ElementType.TYPE, ElementType.METHOD}) //作用到类，方法，接口上等
@Inherited //子类会继承
public @interface TtwApiMenu {
    /**
     * 标题
     *
     * @return
     */
    String value() default "";

    /**
     * request描述
     *
     * @return
     */
    String requestDesc() default "";

    /**
     * response描述
     *
     * @return
     */
    String responseDesc() default "";

}
