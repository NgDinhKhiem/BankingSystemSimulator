package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class AccountVerificationHandler extends AbstractHandler{
    @Param
    private String ID;
    public AccountVerificationHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getAccountVerificationResponse(ID));
    }
}
