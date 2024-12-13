package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class DepositHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String key;
    public DepositHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return new Response(ResponseCode.ACCEPT, server.getDataManager().getSendingMoneyResponse("0000",ID,parseKey()));
    }

    private int parseKey(){
        try {
            return Integer.parseInt(key);
        }catch (Exception e){
            return 0;
        }
    }
}
