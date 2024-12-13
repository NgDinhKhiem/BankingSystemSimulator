package project.cs3360.response;

public class NotificationResponse extends AbstractResponse{
    private final String notification;

    public NotificationResponse(String notification) {
        this.notification = notification;
    }
}
