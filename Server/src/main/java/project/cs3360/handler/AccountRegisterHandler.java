package project.cs3360.handler;

import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.response.RegistrationResponse;
import project.cs3360.utils.Utils;

public class AccountRegisterHandler extends AbstractHandler{
    @Param
    private String firstName;
    @Param
    private String lastName;
    @Param
    private String phoneNumber;
    @Param
    private String email;
    @Param
    private String password;

    public AccountRegisterHandler(Server server) {
        super(server, "GET");
    }

    @Override
    protected Response resolve() {
        Response nullResponse = new Response(ResponseCode.ACCEPT, new RegistrationResponse(false, "INVALID REGISTRATION"));
        if(firstName == null || firstName.isEmpty()) {
            return nullResponse;
        }

        if(lastName == null || lastName.isEmpty()) {
            return nullResponse;
        }

        if (phoneNumber == null || phoneNumber.isEmpty()|| !Utils.isValidPhoneNumber(phoneNumber)) {
            return nullResponse;
        }

        if (email == null || email.isEmpty() || Utils.isValidEmail(email)) {
            return nullResponse;
        }

        if(password == null || password.isEmpty()) {
            return nullResponse;
        }

        return new Response(ResponseCode.ACCEPT, server.getDataManager().getRegistrationResponse(firstName,lastName,phoneNumber,email,password));
    }
}
