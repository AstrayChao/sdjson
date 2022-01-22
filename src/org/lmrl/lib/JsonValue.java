package org.lmrl.lib;

import com.sun.jdi.Value;
import org.lmrl.enums.ValueType;
import org.lmrl.lib.type.ArrayType;
import org.lmrl.lib.type.ObjectType;

/**
 * <p>
 * create at 2022/1/22 17:57
 *
 * @author dxc
 **/
public class JsonValue {
    private String data;
    private ValueType type;
    private ArrayType arrayType;
    private ObjectType objectType;

    public JsonValue() {
    }

    public JsonValue(Boolean b) {
        this.type = ValueType.Boolean;
        this.data = b ? "true" : "false";
    }

    public JsonValue(Integer num) {
        this.type = ValueType.Number;
        this.data = num.toString();
    }

    public JsonValue(String str) {
        this.type = ValueType.String;
        this.data = str;
    }

    public JsonValue(ArrayType arr) {
        this.type = ValueType.Array;
        this.data = "";
        this.arrayType = arr;
    }

    public JsonValue(ObjectType obj) {
        this.type = ValueType.Object;
        this.data = "";
        this.objectType = obj;
    }

    public static void main(String[] args) {
        JsonValue jsonValue = new JsonValue(true);
    }
}
