package app.exceptions;

import app.utils.Utils;

public class ApiSecurityException extends RuntimeException {

    private int code;
    public ApiSecurityException (int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}