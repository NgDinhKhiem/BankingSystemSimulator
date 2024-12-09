package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class SendingMoneyHandler extends AbstractHandler{
    @Param
    private String ID;
    private String token;
    private String receiverID;
    private double amount;
    public SendingMoneyHandler(Server server) {
        super(server, "POST");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getSendingMoneyResponse(ID, receiverID, amount));
    }
}
