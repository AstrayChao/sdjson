package org.lmrl.api;

import org.lmrl.enums.ValueType;
import org.lmrl.type.JsonValue;
import org.lmrl.utils.JsonUtils;

import static org.lmrl.utils.JsonUtils.invalidValue;

public class Parser {

    private static final String VALUE_NULL = "null";
    private static final String VALUE_TRUE = "true";
    private static final String VALUE_FALSE = "false";
    private String content;

    private Parser() {
    }

    private Parser(String content) {
        this.content = content;
    }

    public static JsonValue parse(String content) {
        return new Parser(content).parse();
    }


    public JsonValue parse() {
        if (!JsonUtils.isWhitespace(content)) {
            return null;
        }
        JsonValue value = new JsonValue();
        for (char c : content.toCharArray()) {
            switch (c) {
                case '[' -> value = parseArray();
                case '{' -> value = parseObject();
                default -> {
                    return null;
                }
            }
        }
        if (!value.isValid()) {
            return null;
        }
        if (JsonUtils.isWhitespace(content)) {
            return null;
        }
        return value;
    }


    public JsonValue parseValue() {

        for (char c : content.toCharArray()) {
            switch (c) {
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
        return null;
    }

    private JsonValue parseString() {
        // TODO
        return null;
    }

    public JsonValue parseNull() {
        if (!content.equals(VALUE_NULL)) {
            return invalidValue();
        }
        return new JsonValue();
    }

    public JsonValue parseBoolean() {
        char c = content.charAt(0);
        if (c == 't') {
            if (!content.equals(VALUE_TRUE)) {
                return invalidValue();
            }
            return new JsonValue(true);
        } else if (c == 'f') {
            if (!content.equals(VALUE_FALSE)) {
                return invalidValue();
            }
            return new JsonValue(false);
        }
        return invalidValue();
    }

    public JsonValue parseNumber() {
        int i = 0;
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
        return new JsonValue(ValueType.Number, content.substring(0, i));
    }

    public JsonValue parseArray() {
        // TODO
        return null;
    }

    private JsonValue parseObject() {
        System.out.println();
        return null;
    }

}
