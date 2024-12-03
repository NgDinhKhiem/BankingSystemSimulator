package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class AccountBalanceHandler extends AbstractHandler{
    @Param
    private int lmao;
    @Param
    private boolean state;

    public AccountBalanceHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT,null);
    }
}
