package org.lmrl.type;

import org.lmrl.enums.ValueType;
import org.lmrl.exception.JsonException;
import org.lmrl.utils.JsonUtils;

/**
 * <p>
 * create at 2022/1/22 17:57
 *
 * @author dxc
 **/
public class JsonValue {

    private ValueType type;
    private Object rawData;

    public JsonValue() {
        this.type = ValueType.Null;
        rawData = null;
    }

    public JsonValue(ValueType type, Object rawData) {
        this.type = type;
        this.rawData = rawData;
    }

    public JsonValue(Boolean b) {
        this.type = ValueType.Boolean;
        this.rawData = b ? "true" : "false";
    }

    public JsonValue(Integer num) {
        this.type = ValueType.Number;
        this.rawData = num.toString();
    }

    public JsonValue(Long num) {
        this.type = ValueType.Number;
        this.rawData = num.toString();
    }

    public JsonValue(Float num) {
        this.type = ValueType.Number;
        this.rawData = num.toString();
    }

    public JsonValue(Double num) {
        this.type = ValueType.Number;
        this.rawData = num.toString();
    }

    public JsonValue(String str) {
        this.type = ValueType.String;
        this.rawData = JsonUtils.unescapeString(str);
    }

    public JsonValue(JsonArray arr) {
        this.type = ValueType.Array;
        this.rawData = arr;
    }

    public JsonValue(JsonObject obj) {
        this.type = ValueType.Object;
        this.rawData = obj;
    }


    public ValueType type() {
        return type;
    }

    public JsonObject asObject() {
        if (isObject()) {
            return (JsonObject) rawData;
        }
        return new JsonObject();
    }

    public boolean contains(String key) {
        return isObject() && asObject().contains(key);
    }

    public boolean contains(int pos) {
        return isArray() && asArray().contains(pos);
    }

    public JsonValue get(int pos) {
        return asArray().get(pos);
    }

    public JsonValue find(int pos) {
        return isArray() ? asArray().get(pos) : null;
    }

    public JsonValue get(String key) {
        return asObject().get(key);
    }

    public JsonValue find(String key) {
        return isObject() ? asObject().get(key) : null;
    }

    public boolean isValid() {
        return type != ValueType.Invalid;
    }

    public boolean isObject() {
        return type == ValueType.Object;
    }

    public boolean isBoolean() {
        return type == ValueType.Boolean;
    }

    public boolean isNumber() {
        return type == ValueType.Number;
    }

    public boolean isString() {
        return type == ValueType.String;
    }

    public boolean isArray() {
        return type == ValueType.Array;
    }

    public void clear() {
        this.type = null;
        this.rawData = null;
    }

    public boolean asBoolean() throws JsonException {
        if (isBoolean()) {
            if (rawData.equals("true")) {
                return true;
            } else if (rawData.equals("false")) {
                return false;
            } else {
                throw new JsonException("Unknown Parse Error");
            }
        } else {
            throw new JsonException("Error Type");
        }

    }

    public int asInteger() throws JsonException {
        if (isNumber()) {
            return Integer.parseInt(rawData.toString());
        }
        throw new JsonException("Error Type");
    }

    public long asLong() throws JsonException {
        if (isNumber()) {
            return Long.parseLong(rawData.toString());
        }
        throw new JsonException("Error Type");
    }

    public float asFloat() throws JsonException {
        if (isNumber()) {
            return Float.parseFloat(rawData.toString());
        }
        throw new JsonException("Error Type");
    }

    public double asDouble() throws JsonException {
        if (isNumber()) {
            return Double.parseDouble(rawData.toString());
        }
        throw new JsonException("Error Type");
    }

    public String asString() throws JsonException {
        if (isString()) {
            return JsonUtils.escapeString(rawData.toString());
        }
        throw new JsonException("Error Type");
    }

    public JsonArray asArray() {
        if (isArray()) {
            return (JsonArray) rawData;
        }
        return new JsonArray();
    }

    public String format() {
        return format(false, "    ", 0);
    }

    public String format(boolean keySorted) {
        return format(keySorted, "    ", 0);
    }

    public String format(boolean keySorted, String shift, int shiftCount) {
        switch (type) {
            case Null -> {
                return "null";
            }
            case Boolean, Number -> {
                return rawData.toString();
            }
            case String -> {
                return '"' + rawData.toString() + '"';
            }
            case Array -> {
                return asArray().format(keySorted, shift, shiftCount);
            }
            case Object -> {
                return asObject().format(keySorted, shift, shiftCount);
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public String toString() {
        switch (type) {
            case Null -> {
                return "null";
            }
            case Boolean, Number -> {
                return rawData.toString();
            }
            case String -> {
                return '"' + rawData.toString() + '"';
            }
            case Array -> {
                return asArray().toString();
            }
            case Object -> {
                return asObject().toString();
            }
            default -> {
                return "";
            }
        }
    }
}
