package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Response;

public class AccountVerificationHandler extends AbstractHandler{
    public AccountVerificationHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        return null;
    }
}
