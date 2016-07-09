package smile.common;

import com.fasterxml.jackson.databind.DeserializationFeature;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-06-28
 */
public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
    /**
     * Default constructor, which will construct the default
     * {@link JsonFactory} as necessary, use
     * {@link SerializerProvider} as its
     * {@link SerializerProvider}, and
     * {@link BeanSerializerFactory} as its
     * {@link SerializerFactory}.
     * This means that it
     * can serialize all standard JDK types, as well as regular
     * Java Beans (based on method names and Jackson-specific annotations),
     * but does not support JAXB annotations.
     */
    public ObjectMapper() {
        super();
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
