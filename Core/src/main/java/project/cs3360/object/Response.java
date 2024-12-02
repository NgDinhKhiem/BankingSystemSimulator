package project.cs3360.object;

public class Response {
    private final ResponseCode code;
    private final String response;

    public Response(ResponseCode code, String response) {
        this.code = code;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public ResponseCode getCode() {
        return code;
    }
}
