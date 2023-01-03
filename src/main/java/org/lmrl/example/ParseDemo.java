package org.lmrl.example;

import org.lmrl.exception.JsonException;
import org.lmrl.parse.Parser;
import org.lmrl.type.JsonValue;

import java.util.List;
import java.util.Map;

public class ParseDemo {
    static final String content = """
               {
                    "author": "AstrayChao",
                    "string": "string",
                    "number": -0.031415926E+2,
                    "array": ["hello", "it's me"],
                    "object": {
                        "array": [
                            {
                                "foo": "bar"
                            }
                        ]
                    }
               }
            """;

    public static void main(String[] args) throws JsonException {
        JsonValue parsedJsonContent = Parser.parse(content);
        System.out.println(parsedJsonContent.format());

        System.out.println(parsedJsonContent.get("author").asString());

        System.out.println(parsedJsonContent.get("number").asDouble());

        List<JsonValue> jsonValues = parsedJsonContent.get("array").asArray().toList();
        for (var jsonValue : jsonValues) {
            System.out.println(jsonValue.asString());
        }

        Map<String, JsonValue> object = parsedJsonContent.get("object").asObject().toMap();
        for (var entry : object.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        JsonValue notExists = parsedJsonContent.get("not exists");
        System.out.println(notExists);
    }

}
