package org.lmrl.type;

import org.lmrl.exception.JsonException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * create at 2022/1/22 22:46
 *
 * @author dxc
 **/
public class JsonArray {
    private final List<JsonValue> arrayData;

    public JsonArray() {
        arrayData = new ArrayList<>(0);
    }

    public JsonArray(int size) {
        arrayData = new ArrayList<>(size);
    }

    public JsonArray(JsonValue value) throws JsonException {
        JsonArray jsonArray = value.asArray();
        this.arrayData = jsonArray.arrayData;
    }

    public JsonArray(List<JsonValue> arrayData) {
        this.arrayData = arrayData;
    }

    public boolean empty() {
        return arrayData.isEmpty();
    }

    public int size() {
        return arrayData.size();
    }

    public boolean contains(int pos) {
        return pos < arrayData.size();
    }

    public JsonValue get(int pos) {
        if (contains(pos)) {
            return arrayData.get(pos);
        } else {
            return null;
        }
    }

    public boolean get(int pos, boolean defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isBoolean()) {
                return value.asBoolean();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public int get(int pos, int defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isNumber()) {
                return value.asInteger();

            } else {
                return defaultValue;

            }
        } else {
            return defaultValue;
        }
    }

    public long get(int pos, long defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isNumber()) {
                return value.asLong();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public double get(int pos, double defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isNumber()) {
                return value.asDouble();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public float get(int pos, float defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isNumber()) {
                return value.asFloat();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public boolean add(JsonValue value) {
        return arrayData.add(value);
    }

    public String get(int pos, String defaultValue) throws JsonException {
        if (contains(pos)) {
            var value = arrayData.get(pos);
            if (value.isString()) {
                return value.asString();
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public void clear() {
        arrayData.clear();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (var value : arrayData) {
            str.append(value.toString()).append(",");
        }
        if (str.charAt(str.length() - 1) == ',') {
            str.deleteCharAt(str.length() - 1);
        }
        str.append("]");
        return str.toString();
    }

    public String format(boolean ordered, String shift, int shiftCount) {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (var value : arrayData) {
            str.append("\n").append(String.valueOf(shift).repeat(Math.max(0, shiftCount + 1)))
                    .append(value.format(ordered, shift, shiftCount + 1)).append(",");
        }
        if (str.charAt(str.length() - 1) == ',') {
            str.deleteCharAt(str.charAt(str.length() - 1));
        }
        str.append("\n");
        str.append(String.valueOf(shift).repeat(Math.max(0, shiftCount))).append("]");
        return str.toString();
    }
}

