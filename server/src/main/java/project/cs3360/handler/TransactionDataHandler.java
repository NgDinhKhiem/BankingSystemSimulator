package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.TransactionIDResponse;
import project.cs3360.response.object.TransactionData;

public class TransactionDataHandler extends AbstractHandler{
    @Param
    private String transactionID;
    @Param
    private String ID;
    @Param
    private String token;
    public TransactionDataHandler(Server server) {
        super(server, "GET");
    }
    @Override
    protected Response resolve() {
        if(!server.getDataManager().isValid(this.ID, this.token)){
            return new Response(ResponseCode.ACCEPT, new TransactionIDResponse(new TransactionData()));
        }
        return new Response(ResponseCode.GOOD, server.getDataManager().getTransactionDataResponse(transactionID));
    }
}
