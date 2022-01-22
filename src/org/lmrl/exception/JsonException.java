package org.lmrl.exception;

/**
 * <p>
 * create at 2022/1/22 17:46
 *
 * @author dxc
 **/
public class JsonException extends Exception{

    protected String message;

    public JsonException() {}
    public JsonException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message.isEmpty() ? "Unknown Exception" : message;
    }
}
