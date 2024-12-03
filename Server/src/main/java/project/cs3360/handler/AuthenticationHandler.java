package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.AuthenticationResponse;

public class AuthenticationHandler extends AbstractHandler{
    @Param
    private String username;
    @Param
    private String password;
    public AuthenticationHandler(Server server) {
        super(server, "GET");
    }
    @Override
    protected Response resolve() {
        return new Response(ResponseCode.GOOD, new AuthenticationResponse(true,"HelloToken"));
    }
}
