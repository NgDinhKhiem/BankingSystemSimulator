package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.NotificationResponse;

public class NotificationHandler extends AbstractHandler{
    @Param
    private String ID;
    @Param
    private String token;

    public NotificationHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        if(!server.getDataManager().isValid(this.ID, this.token)){
            return new Response(ResponseCode.ACCEPT, new NotificationResponse("{}"));
        }
        return new Response(ResponseCode.ACCEPT, server.getNotificationManager().getNotificationResponse(ID));
    }
}
