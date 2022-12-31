package org.lmrl.wtf;

import org.lmrl.enums.ValueType;
import org.lmrl.exception.JsonException;
import org.lmrl.utils.JsonUtils;
import org.lmrl.wtf.type.JsonArray;
import org.lmrl.wtf.type.JsonObject;

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
        this.type = ValueType.Number;
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

    public boolean isObject() {
        return type == ValueType.Object;
    }

    public JsonObject asObject() throws JsonException {
        if (isObject()) {
            return (JsonObject) rawData;
        }
        throw new JsonException("Error Type or data is empty");
    }

    public boolean contains(String key) throws JsonException {
        return isObject() && asObject().contains(key);
    }

    public boolean isBoolean() {
        return type == ValueType.Boolean;
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
            case Object -> {
                try {
                    return asObject().toString();
                } catch (JsonException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                try {
                    throw new JsonException("Unknown Value type");
                } catch (JsonException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public boolean isNumber() {
        return type == ValueType.Number;
    }

    public int asInteger() throws JsonException {
        if (isNumber()) {
            return Integer.parseInt(rawData.toString());
        } else {
            throw new JsonException("Error Type");
        }
    }
}
