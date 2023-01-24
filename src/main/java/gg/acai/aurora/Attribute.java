package gg.acai.aurora;

import java.util.function.Supplier;

/**
 * @author Clouke
 * @since 23.01.2023 18:46
 * © Clouke Services - All Rights Reserved
 */
public final class Attribute {

    private final String key;
    private final Object value;

    public Attribute(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public Object value() {
        return value;
    }

    public <T> T valueAs(Supplier<Class<T>> type)
        throws ClassCastException {
            Class<T> clazz = type.get();
            return clazz.cast(value);
        }
}

