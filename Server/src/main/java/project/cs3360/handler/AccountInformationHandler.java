package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.AccountInformationResponse;

public class AccountInformationHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String token;
    public AccountInformationHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        if(!server.getDataManager().isValid(this.ID, this.token)){
            return new Response(ResponseCode.ACCEPT, new AccountInformationResponse(false,"","","","",""));
        }
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getAccountInformation(ID));
    }
}
