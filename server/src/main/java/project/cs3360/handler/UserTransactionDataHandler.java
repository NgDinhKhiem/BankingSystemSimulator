package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

public class UserTransactionDataHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String token;
    @Param
    private int startIndex;
    @Param
    private int endIndex;
    @Param
    private String startDate;
    @Param
    private String endDate;
    public UserTransactionDataHandler(Server server) {
        super(server, "GET");
    }
    @Override
    protected Response resolve() {
        return new Response(ResponseCode.GOOD, server.getDataManager().getTransactionInformationResponse(ID, startIndex, endIndex, startDate, endDate));
    }
}
