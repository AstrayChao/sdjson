package org.lmrl.parse;

import org.lmrl.enums.ValueType;
import org.lmrl.type.JsonArray;
import org.lmrl.type.JsonObject;
import org.lmrl.type.JsonValue;
import org.lmrl.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private static final String VALUE_NULL = "null";
    private static final String VALUE_TRUE = "true";
    private static final String VALUE_FALSE = "false";
    private int i = 0;
    private String content;

    private Parser() {
    }

    private Parser(String content) {
        this.content = content;
    }

    public static JsonValue parse(String content) {
        if (content == null || content.isBlank()) {
            return new JsonValue();
        }
        return new Parser(content).parse();
    }

    private JsonValue invalidValue() {
        return new JsonValue(ValueType.Invalid, null);
    }

    private boolean isWhitespace() {
        while (i != content.length()) {
            switch (content.charAt(i)) {
                case ' ', '\t', '\n', 'r' -> i++;
                default -> {
                    return true;
                }
            }
        }
        return false;
    }

    private JsonValue parse() {
        if (!isWhitespace()) {
            return null;
        }
        JsonValue value;
        switch (content.charAt(i)) {
            case '[' -> value = parseArray();
            case '{' -> value = parseObject();
            default -> {
                return null;
            }
        }
        if (!value.isValid()) {
            return null;
        }
        if (isWhitespace()) {
            return null;
        }
        return value;
    }

    private JsonValue parseValue() {
        switch (content.charAt(i)) {
            case 'n' -> {
                return parseNull();
            }
            case 't', 'f' -> {
                return parseBoolean();
            }
            case '-', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> {
                return parseNumber();
            }
            case '"' -> {
                return parseString();
            }
            case '[' -> {
                return parseArray();
            }
            case '{' -> {
                return parseObject();
            }
            default -> {
                return invalidValue();
            }
        }
    }

    private JsonValue parseString() {
        String str = parseStr();
        return str == null ? invalidValue() : new JsonValue(ValueType.String, str);
    }

    private String parseStr() {
        char c = content.charAt(i);
        if (c == '"') {
            i++;
        } else {
            return null;
        }
        int first = i, last = i;
        boolean isStartingEnd = false;
        while (!isStartingEnd && i != content.length()) {
            switch (content.charAt(i)) {
                case '\t', '\r', '\n' -> {
                    return null;
                }
                case '\\' -> {
                    if (++i == content.length()) {
                        return null;
                    }
                    switch (content.charAt(i)) {
                        case '"', '\\', '/', 'b', 'f', 'n', 'r', 't', 'u' -> i++;
                        default -> {
                            return null;
                        }
                    }
                }
                case '"' -> {
                    last = i;
                    i++;
                    isStartingEnd = true;
                }
                default -> i++;
            }
        }
        if (i == content.length()) {
            return null;
        }
        return content.substring(first, last);
    }

    private JsonValue parseNull() {
        if (!content.equals(VALUE_NULL)) {
            return invalidValue();
        }
        return new JsonValue();
    }

    private JsonValue parseBoolean() {
        char c = content.charAt(i);
        switch (c) {
            case 't' -> {
                for (char ch : VALUE_TRUE.toCharArray()) {
                    if (content.charAt(i) == ch) {
                        i++;
                    } else {
                        return invalidValue();
                    }
                }
                return new JsonValue(true);
            }
            case 'f' -> {
                for (char ch : VALUE_FALSE.toCharArray()) {
                    if (content.charAt(i) == ch) {
                        i++;
                    } else {
                        return invalidValue();
                    }
                }
                return new JsonValue(false);
            }
            default -> {
                return invalidValue();
            }
        }
    }

    private JsonValue parseNumber() {
        int first = i;
        // 负号
        if (content.charAt(i) == '-') {
            i++;
        }
        // 整数(要处理前导零
        if (content.charAt(i) == '0') {
            i++;
        } else {
            if (!Character.isDigit(content.charAt(i))) {
                return invalidValue();
            }
            while (i < content.length() && Character.isDigit(content.charAt(i))) {
                i++;
            }
        }
        // 小数
        if (content.charAt(i) == '.') {
            i++;
            if (!Character.isDigit(content.charAt(i))) {
                return invalidValue();
            }
            while (i < content.length() && Character.isDigit(content.charAt(i))) {
                i++;
            }
        }
        // 指数
        if (content.charAt(i) == 'e' || content.charAt(i) == 'E') {
            i++;
            if (content.charAt(i) == '+' || content.charAt(i) == '-') {
                i++;
            }
            if (!Character.isDigit(content.charAt(i))) {
                return invalidValue();
            }
            while (i < content.length() && Character.isDigit(content.charAt(i))) {
                i++;
            }
        }
        return new JsonValue(ValueType.Number, content.substring(first, i));
    }

    private JsonValue parseArray() {
        if (content.charAt(i) == '[') {
            i++;
        } else {
            return invalidValue();
        }
        if (!isWhitespace()) {
            return invalidValue();
        } else if (content.charAt(i) == ']') {
            i++;
            return new JsonValue(new JsonArray());
        }
        List<JsonValue> result = new ArrayList<>(4);
        while (true) {
            if (!isWhitespace()) {
                return invalidValue();
            }
            JsonValue value = parseValue();
            if (!value.isValid() || !isWhitespace()) {
                return invalidValue();
            }
            result.add(value);
            if (content.charAt(i) == ',') {
                i++;
            } else {
                break;
            }
        }
        if (isWhitespace() && content.charAt(i) == ']') {
            i++;
        } else {
            return invalidValue();
        }
        return new JsonValue(new JsonArray(result));
    }

    private JsonValue parseObject() {
        if (content.charAt(i) == '{') {
            i++;
        } else {
            return invalidValue();
        }
        if (!isWhitespace()) {
            return invalidValue();
        } else if (content.charAt(i) == '}') {
            i++;
            return new JsonValue(new JsonObject());
        }
        Map<String, JsonValue> result = new HashMap<>(4);
        while (true) {
            if (!isWhitespace()) {
                return invalidValue();
            }
            String key = parseStr();
            if (key != null && isWhitespace() && content.charAt(i) == ':') {
                i++;
            } else {
                return invalidValue();
            }
            if (!isWhitespace()) {
                return invalidValue();
            }
            JsonValue value = parseValue();
            if (!value.isValid() || !isWhitespace()) {
                return invalidValue();
            }
            String escapedKey = JsonUtils.escapeString(key);
            result.put(escapedKey, value);
            if (content.charAt(i) == ',') {
                i++;
            } else {
                break;
            }
        }
        if (isWhitespace() && content.charAt(i) == '}') {
            i++;
        } else {
            return invalidValue();
        }
        return new JsonValue(new JsonObject(result));
    }
}
