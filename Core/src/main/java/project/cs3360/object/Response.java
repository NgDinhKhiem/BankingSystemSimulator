package project.cs3360.object;

import project.cs3360.response.AbstractResponse;

public class Response {
    private final ResponseCode code;
    private final AbstractResponse response;

    public Response(ResponseCode code, AbstractResponse response) {
        this.code = code;
        this.response = response;
    }

    public AbstractResponse getResponse() {
        return response;
    }

    public ResponseCode getCode() {
        return code;
    }
}
