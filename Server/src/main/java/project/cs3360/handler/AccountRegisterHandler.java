package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.RegistrationResponse;

public class AccountRegisterHandler extends AbstractHandler{
    @Param
    private String firstName;
    @Param
    private String lastName;
    @Param
    private String phoneNumber;
    @Param
    private String email;
    @Param
    private String password;

    public AccountRegisterHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getRegistrationResponse(firstName,lastName,phoneNumber,email,password));
    }
}
