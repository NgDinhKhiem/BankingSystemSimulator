package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.SendingMoneyResponse;

public class SendingMoneyHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String token;
    @Param
    private String receiverID;
    @Param
    private double amount;
    public SendingMoneyHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        if(!server.getDataManager().isValid(this.ID, this.token)){
            return new Response(ResponseCode.ACCEPT, new SendingMoneyResponse(false,"null"));
        }
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getSendingMoneyResponse(ID, receiverID, amount));
    }
}
