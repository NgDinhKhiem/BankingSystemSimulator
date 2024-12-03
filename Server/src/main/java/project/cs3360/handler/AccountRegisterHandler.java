package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class AccountRegisterHandler extends AbstractHandler{
    @Param
    private String firstName;
    @Param
    private String lastName;
    @Param
    private String phoneNumber;
    @Param
    private String email;

    public AccountRegisterHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT, null);
    }
}
