package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.AccountBalanceResponse;

public class AccountBalanceHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String token;

    public AccountBalanceHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        if(!server.getDataManager().isValid(this.ID, this.token)){
            return new Response(ResponseCode.ACCEPT, new AccountBalanceResponse(false,-1));
        }

        return new Response(ResponseCode.ACCEPT,server.getDataManager().getAccountBalance(this.ID));
    }
}
