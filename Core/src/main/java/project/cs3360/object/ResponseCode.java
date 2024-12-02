package project.cs3360.object;

public enum ResponseCode {
    GOOD(200),
    CREATED(201),
    ACCEPT(202),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    ;


    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
