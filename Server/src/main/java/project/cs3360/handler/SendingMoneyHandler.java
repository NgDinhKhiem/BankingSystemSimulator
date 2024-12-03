package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Response;

public class SendingMoneyHandler extends AbstractHandler{
    public SendingMoneyHandler(Server server) {
        super(server, "POST");
    }

    @Override
    protected Response resolve() {
        return null;
    }
}
