package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.manager.data.notification.noti.TransactionNotification;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.NotificationResponse;

public class TestHandler extends AbstractHandler{
    public TestHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        server.getNotificationManager().pushNotification(new TransactionNotification("SUCCESS","12312312"),"59099706");
        return new Response(ResponseCode.ACCEPT, new NotificationResponse("Hello World!"));
    }
}
