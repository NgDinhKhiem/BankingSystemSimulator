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
        if(email!=null){
            email = email.replace("%3dot%3",".");
        }
        Response nullResponse = new Response(ResponseCode.ACCEPT, new RegistrationResponse(false, "INVALID REGISTRATION"));
        if(firstName == null || firstName.isEmpty()) {
            System.out.println("firstName is null or empty");
            return nullResponse;
        }

        if(lastName == null || lastName.isEmpty()) {
            System.out.println("lastName is null or empty");
            return nullResponse;
        }

        if (phoneNumber == null || phoneNumber.isEmpty()|| !Utils.isValidPhoneNumber(phoneNumber)) {
            System.out.println("phoneNumber is null or empty");
            return nullResponse;
        }

        if (email == null || email.isEmpty() || Utils.isValidEmail(email)) {
            System.out.println("email is null or empty");
            return nullResponse;
        }

        if(password == null || password.isEmpty()) {
            System.out.println("password is null or empty");
            return nullResponse;
        }

        return new Response(ResponseCode.ACCEPT, server.getDataManager().getRegistrationResponse(firstName,lastName,phoneNumber,email,password));
    }
}
