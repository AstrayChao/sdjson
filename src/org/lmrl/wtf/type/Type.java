package org.lmrl.wtf.type;

import org.lmrl.enums.ValueType;
import org.lmrl.exception.JsonException;

public abstract class Type {

    protected abstract boolean contains(String key);

    protected abstract JsonValue get(String key);

    private <T> Object function(JsonValue value, T clz) throws JsonException {
        ValueType type = value.type();
        if (type == ValueType.Boolean) {
            return value.asBoolean();
        } else if (type == ValueType.Number) {
            if (clz instanceof Integer) {
                return value.asInteger();
            } else if (clz instanceof Float) {
                return value.asFloat();
            } else if (clz instanceof Double) {
                return value.asDouble();
            }
        } else if (type == ValueType.String) {
            return value.asString();
        }
        return null;
    }

    public <T> T get(String key, T defaultValue) throws JsonException {
        if (contains(key)) {
            JsonValue value = get(key);
            Object res = function(value, defaultValue.getClass());
            if (res != null) {
                return (T) res;
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
