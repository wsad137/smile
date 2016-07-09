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
public @interface Api {
    /**
     * 标题
     *
     * @return
     */
    String value() default "";

    /**
     * 详细说明
     *
     * @return
     */
    String desc() default "";

    /**
     * <pre>
     *     item: { 字段名称 / 字段说明 / 字段类型 / 默认值 }
     *     [/]分割
     *     默认值，为time时，会直接转成时间戳
     * </pre>
     *
     * @return
     */
    String[] names() default {};

    /**
     * @return
     */
    Class[] beans() default {};


}
