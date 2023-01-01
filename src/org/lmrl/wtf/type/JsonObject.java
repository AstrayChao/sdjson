package org.lmrl.wtf.type;

import org.lmrl.exception.JsonException;
import org.lmrl.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>
 * create at 2022/1/22 22:47
 *
 * @author dxc
 **/
public class JsonObject {

    private final Map<String, JsonValue> rawObject;

    public JsonObject() {
        rawObject = new HashMap<>(0);
    }

    public JsonObject(Map<String, JsonValue> rawObject) {
        this.rawObject = rawObject;
    }

    // 考虑用Function或者Predicate去做统一的抽象, 但是使用太麻烦...故写多个版本的get函数
    public int get(String key, int defaultValues) throws JsonException {
        if (contains(key)) {
            var value = get(key);
            if (value.isNumber()) {
                return value.asInteger();
            } else {
                return defaultValues;
            }
        } else {
            return defaultValues;
        }
    }

    public boolean get(String key, boolean defaultValues) throws JsonException {
        if (contains(key)) {
            var value = get(key);
            if (value.isBoolean()) {
                return value.asBoolean();
            } else {
                return defaultValues;
            }
        } else {
            return defaultValues;
        }
    }

    public long get(String pos, long defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = get(pos);
            if (value.isNumber()) {
                return value.asLong();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public double get(String pos, double defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = get(pos);
            if (value.isNumber()) {
                return value.asDouble();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public float get(String pos, float defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = get(pos);
            if (value.isNumber()) {
                return value.asFloat();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public String get(String pos, String defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = get(pos);
            if (value.isString()) {
                return value.asString();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
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

    public String format() {
        return format(true, "    ", 0);
    }

    public String format(boolean sorted, String shift, int shiftCount) {
        StringBuilder str = new StringBuilder();
        str.append("{");
        BiConsumer<String, JsonValue> appendConsumer = (String key, JsonValue val) ->
                str.append("\n")
                        .append(String.valueOf(shift).repeat(Math.max(0, shiftCount + 1)))
                        .append("\"").append(JsonUtils.unescapeString(key))
                        .append("\": ").append(val.format(sorted, shift, shiftCount + 1)).append(",");
        var iterator = rawObject.entrySet().iterator();
        if (sorted) {
            List<Map.Entry<String, JsonValue>> orderedData = new ArrayList<>();
            while (iterator.hasNext()) {
                orderedData.add(iterator.next());
            }
            orderedData.sort(Map.Entry.comparingByKey());
            for (var it : orderedData) {
                appendConsumer.accept(it.getKey(), it.getValue());
            }
        } else {
            for (var entry : rawObject.entrySet()) {
                appendConsumer.accept(entry.getKey(), entry.getValue());
            }
        }
        if (str.charAt(str.length() - 1) == ',') {
            str.deleteCharAt(str.length() - 1);
        }
        str.append("\n");
        str.append(String.valueOf(shift).repeat(Math.max(0, shiftCount))).append("}");
        return str.toString();
    }
}



