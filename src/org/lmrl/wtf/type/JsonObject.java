package org.lmrl.wtf.type;

import org.lmrl.utils.JsonUtils;
import org.lmrl.wtf.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 * create at 2022/1/22 22:47
 *
 * @author dxc
 **/
public class JsonObject {

    Map<String, JsonValue> rawObject = new HashMap<>();

    public JsonObject() {
    }

    public JsonObject(Map<String, JsonValue> rawObject) {
        this.rawObject = rawObject;
    }

    public int size() {
        return rawObject.size();
    }

    public boolean empty() {
        return rawObject.isEmpty();
    }

    public boolean exists(String key) {
        return contains(key);
    }

    public boolean contains(String key) {
        return rawObject.containsKey(key);
    }

    public JsonValue get(String key) {
        return rawObject.get(key);
    }

    public JsonValue put(String key, JsonValue values) {
        return rawObject.put(key, values);
    }

    /**
     * public boolean get(String key, boolean defaultValue) throws JsonException {
     * if (contains(key)) {
     * JsonValue value = get(key);
     * if (value.isBoolean()) {
     * return value.asBoolean();
     * } else {
     * return defaultValue;
     * }
     * } else {
     * return defaultValue;
     * }
     * }
     * <p>
     * public int get(String key, int defaultValue) throws JsonException {
     * if (contains(key)) {
     * JsonValue value = get(key);
     * if (value.isNumber()) {
     * return value.asInteger();
     * } else {
     * return defaultValue;
     * }
     * } else {
     * return defaultValue;
     * }
     * }
     */

    public <T> T get(String key, T defaultValues, Function<JsonValue, T> function) {
        if (contains(key)) {
            JsonValue value = get(key);
            return function.apply(value);
//            if (predicate.test(value)) {
//                return function.apply(value);
//            } else {
//                return defaultValues;
//            }
        } else {
            return defaultValues;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (var entry : rawObject.entrySet()) {
            sb.append("\"")
                    .append(JsonUtils.unescapeString(entry.getKey()))
                    .append("\":")
                    .append(entry.getValue())
                    .append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public String format(boolean ordered, String shift, int shiftCount) {
        // TODO
        return "";
    }
}

